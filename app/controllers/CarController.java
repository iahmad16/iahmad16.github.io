package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.*;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.inject.Inject;
import models.classes.*;
import play.filters.csrf.CSRF;
import play.libs.Json;
import play.libs.ws.*;
import play.mvc.*;

import static controllers.Helper.*;
import static models.enums.CarBodyType.*;
import static models.enums.CarFuelType.*;
import static models.enums.CarGearType.*;
import static models.enums.CarSeatType.*;
import static models.enums.CarWheelDrive.*;
import static models.enums.CarListingStatus.*;

@CheckWATExpiration
public class CarController extends Controller {
    private static WSClient ws;

    @Inject
    public CarController(WSClient ws) {
        this.ws = ws;
    }

    // Returns the Car with the specified listingID. Filters out specific car from car list provided by getCarListings.
    public static Car getCar(Http.Request request, String listingID){
        List<Car> carList = getCarListings(request);
        return carList.stream().filter(car -> listingID.equals(car.listingID)).findFirst().orElse(null);
    }

    // Sends GET request to API to get access to a car's image
    public static String getImage(String url, String WAT){
        return ws.url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .get()
                .thenApply(response -> {
                    if(response.getStatus() == 200){
                        String contentType = response.getContentType();
                        String imageType = contentType.split("/")[1];
                        byte[] imageData = response.asByteArray();
                        return "data:image/" + imageType + ";base64," + Base64.getEncoder().encodeToString(imageData);
                    } else {
                        return "Error: Non-200 response";
                    }
                }).toCompletableFuture().join();

    }

    // Sends GET request to API, fetches the Car List, maps nodes to objects and returns list of
    // Car objects
    public static List<Car> getCarListings(Http.Request request){
        String WAT = request.session().get("WAT").orElse("None");
        return ws.url("http://161.35.78.39:9000/admins/listings/cars")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .get()
                .thenApply(response -> {
                    try
                    {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Car> carList = new ArrayList<>();
                        List<JsonNode> car_list = objectMapper.readerForListOf(JsonNode.class).readValue(response.asJson().get("SUCCESS"));
                        List<JsonNode> carlist = car_list.stream().filter(car -> !(car.get("status").asText().equals("BOOKED") || car.get("status").asText().equals("UNDER_PROCESSING"))).collect(Collectors.toList());
                        for (JsonNode car_json : carlist) {

                            List<String> imagesList = objectMapper.convertValue(car_json.get("images"), new TypeReference<>() {});
                            List<String> imagesList_modified = imagesList.stream().map(image -> getImage(image,WAT)).collect(Collectors.toList());

                            Car car = new Car(
                                    car_json.get("listingID").asText(),
                                    car_json.get("listingDate").asLong(),
                                    SuperAdminController.getCustomer(car_json.get("customerUID").asText(),request),
                                    car_json.get("car").get("id").asText(),
                                    car_json.get("car").get("make").asText(),
                                    car_json.get("car").get("model").asText(),
                                    car_json.get("car").get("subClass").asText(),
                                    car_json.get("car").get("modelYear").asInt(),
                                    car_json.get("car").get("mileage").asLong(),
                                    toCarGearType(car_json.get("car").get("gearType").asText()),
                                    toCarFuelType(car_json.get("car").get("fuelType").asText()),
                                    toCarBodyType(car_json.get("car").get("bodyType").asText()),
                                    car_json.get("car").get("underWarranty").asBoolean(),
                                    car_json.get("car").get("warrantyExpiry").asInt(),
                                    car_json.get("car").get("exteriorColor").asText(),
                                    car_json.get("car").get("interiorColor").asText(),
                                    toCarSeatType(car_json.get("car").get("seatType").asText()),
                                    toCarWheelDrive(car_json.get("car").get("wheelDrive").asText()),
                                    car_json.get("car").get("hasSunRoof").asBoolean(),
                                    car_json.get("car").get("importCountry").asText(),
                                    car_json.get("price").asLong(),
                                    car_json.get("currency").asText(),
                                    toCarListingStatus(car_json.get("status").asText()),
                                    car_json.get("views").asInt(),
                                    car_json.get("soldDate").asLong(),
                                    car_json.get("soldPrice").asLong(),
                                    imagesList_modified
                            );
                            carList.add(car);
                        }
                        return carList;
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }).toCompletableFuture().join();
    }

    // Retrieves car list from getCarListings and renders car listings view
    public Result getCarListingsView(Http.Request request) {

        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);
        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        List<Car> carList = getCarListings(request);
        int soldCars = (int) carList.stream().filter(car -> car.status.equals(SOLD)).count();
        int activeCars = carList.size() - soldCars;

        return ok(views.html.car.car_listings.render(csrfToken,carList,activeCars,soldCars,getRole(request)));
    }

    // Retrieves car from getCar and renders car details view
    public Result getCarDetailsView(String listingID, Http.Request request){

        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);
        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        Car selectedCar = getCar(request,listingID);
        return ok(views.html.car.car_details.render(selectedCar, csrfToken, getRole(request)));
    }

    // Renders add new car view
    public Result getAddCarView(Http.Request request){

        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);
        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        List<CarModel> carModelList = CarModelController.getCarModelListings(request);

        return ok(views.html.car.add_car.render(carModelList, csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing inputted information from add car view
    public CompletionStage<Result> postAddCarView(Http.Request request) {

        JsonNode car_input_json = request.body().asJson();
        String body_type_input = car_input_json.get("bodyType").asText();
        String fuel_type_input = car_input_json.get("fuelType").asText();
        String gear_type_input = car_input_json.get("gearType").asText();
        String seat_type_input = car_input_json.get("seatType").asText();
        String wheel_drive_input = car_input_json.get("wheelDrive").asText();
        JsonNode images_input = car_input_json.get("images");

        List<String> images = new ArrayList<>();
        images_input.forEach(element -> images.add(element.asText()));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode modifiedNode = objectMapper.convertValue(car_input_json, ObjectNode.class);

        modifiedNode.put("bodyType",toCarBodyType(body_type_input).ordinal());
        modifiedNode.put("fuelType",toCarFuelType(fuel_type_input).ordinal());
        modifiedNode.put("gearType",toCarGearType(gear_type_input).ordinal());
        modifiedNode.put("seatType",toCarSeatType(seat_type_input).ordinal());
        modifiedNode.put("wheelDrive",toCarWheelDrive(wheel_drive_input).ordinal());

        ArrayNode arrayNode = objectMapper.valueToTree(images);
        modifiedNode.set("images", arrayNode);
        JsonNode convertedNode = objectMapper.convertValue(modifiedNode,JsonNode.class);
        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/listings/cars/create-listing")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(convertedNode)
                .thenApply(response ->  {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

    // Renders update price view for the specific car listing
    public Result getUpdateListingPriceView(String listingID, Http.Request request){

        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);
        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.car.update_price.render(getCar(request,listingID),csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing inputted information (new price)
    // from update price view
    public CompletionStage<Result> postUpdateListingPriceView(Http.Request request){
        JsonNode car_details_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/listings/cars/update-price")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(car_details_json)
                .thenApply(response ->  {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

    // Sends POST request to API with JSON object containing purchase information
    // Returns a listing associated to that customer
    public CompletionStage<Result> purchaseCarListing(Http.Request request){

        String WAT = request.session().get("WAT").orElse("None");
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrow = currentDate.plusDays(1);
        Long tomorrow_unix = tomorrow.atStartOfDay().toEpochSecond(ZoneOffset.UTC);

        JsonNode purchase_node = Json.newObject()
                .put("transactionID", "123456")
                .put("appointmentDate", tomorrow_unix)
                .put("customerUID","681b7b93ec6642a3a0bd5e815a0f12f4");

        return ws.url("http://161.35.78.39:9000/admins/imaaz/purchaseCarListing")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(purchase_node)
                .thenApply(response -> {
                    System.out.println(response.getBody());
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

    // Sends POST request to API with JSON object containing listingID of the car that is to be
    // set under processing. Displays success message but doesn't redirect.
    public CompletionStage<Result> setUnderProcessing(String listingID, Http.Request request){

        String WAT = request.session().get("WAT").orElse("None");
        JsonNode purchase_node = Json.newObject().put("listingID", listingID);

        return ws.url("http://161.35.78.39:9000/admins/listings/cars/set-under-processing")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(purchase_node)
                .thenApply(response -> {
                    if (response.getStatus() == 200)
                    {
                        return ok("Car with ListingID (" + listingID + "), has been set Under Processing");
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

    // Sends POST request to API with JSON object containing information (listingID & soldPrice)
    // sent via javascript method set_as_sold(listingID,price). If successful, set_as_sold redirects
    // to car listings view
    public CompletionStage<Result> setCarAsSold(Http.Request request){
        JsonNode car_details_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/listings/cars/set-sold")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(car_details_json)
                .thenApply(response -> Results.status(response.getStatus(), response.getStatusText()));
    }
}
