package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletionStage;
import models.classes.*;
import play.filters.csrf.CSRF;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.mvc.*;
import javax.inject.Inject;
import static controllers.Helper.*;

@CheckWATExpiration
public class CarModelController extends Controller {
    private static WSClient ws;

    @Inject
    public CarModelController(WSClient ws) {
        this.ws = ws;
    }

    // Retrieves car model listings from getCarModelListings and returns the model with the
    // specified make
    public CarModel getCarModel(String make, Http.Request request){
        List<CarModel> carModelList = getCarModelListings(request);
        return carModelList.stream().filter(model -> make.equals(model.make)).findFirst().orElse(null);
    }

    // Sends GET request to API to get access to the make logo image
    public static String getMakeLogo(String url, String WAT){
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

    // Sends GET request to API, fetches the Car Model List, maps nodes to objects and returns list of
    // CarModel objects
    public static List<CarModel> getCarModelListings(Http.Request request){

        String WAT = request.session().get("WAT").orElse("None");
        return ws.url("http://161.35.78.39:9000/car-models")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .get()
                .thenApply(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<CarModel> modelList = new ArrayList<>();
                        List<JsonNode> model_list = objectMapper.readerForListOf(JsonNode.class).readValue(response.asJson().get("SUCCESS"));
                        for (JsonNode modelJson : model_list) {
                            CarModel carModel = new CarModel(modelJson.get("make").asText(),getMakeLogo(modelJson.get("logoURL").asText(),WAT), modelJson.get("classes"));
                            modelList.add(carModel);
                        }
                        return modelList;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toCompletableFuture().join();
    }

    // Retrieves car model list from getCarModelListings and renders car model listings view
    public Result getCarModelListingsView(Http.Request request){
        return ok(views.html.car_model.car_model_listings.render(getCarModelListings(request),getRole(request)));
    }

    // Renders the add car model view
    public Result getAddCarModelView(Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.car_model.add_car_model.render(csrfToken, getRole(request)));
    }

    // Sends POST request to API with a JSON object containing inputted information from add car model view
    public CompletionStage<Result> postAddCarModel(Http.Request request){
        JsonNode car_model_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");
        String make_input = car_model_json.get("make").asText();
        String logo_input = car_model_json.get("logo").asText();
        JsonNode classes_input = car_model_json.get("classes");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.convertValue(classes_input, ObjectNode.class);

        JsonNode shop_input_data_modified = Json.newObject()
                .put("make", make_input)
                .put("logo", logo_input)
                .putPOJO("classes", objectNode);


        return ws.url("http://161.35.78.39:9000/admins/data/car-models/add")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(shop_input_data_modified)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

    // Renders edit car make logo view
    public Result getEditCarMakeLogoView(String make, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        return ok(views.html.car_model.edit_car_make_logo.render(getCarModel(make, request),csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing updated make logo from edit car make logo view
    public CompletionStage<Result> postEditCarMakeLogo(Http.Request request){
        JsonNode make_logo_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");
        return ws.url("http://161.35.78.39:9000/admins/data/car-models/update")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(make_logo_json)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

    // Renders add class view for the specified make
    public Result getAddClassView(String make, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        return ok(views.html.car_model.add_class.render(getCarModel(make, request),csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing inputted class information from add class view
    public CompletionStage<Result> postAddClass(Http.Request request){
        JsonNode car_model_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");
        String make_input = car_model_json.get("make").asText();
        JsonNode classes_input = car_model_json.get("classes");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.convertValue(classes_input, ObjectNode.class);

        JsonNode shop_input_data_modified = Json.newObject()
                .put("make", make_input)
                .putPOJO("logo", null)
                .putPOJO("classes", objectNode);


        return ws.url("http://161.35.78.39:9000/admins/data/car-models/add")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(shop_input_data_modified)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });

    }

    // Renders delete class view for the specified make
    public Result getDeleteClassView(String make, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        return ok(views.html.car_model.delete_class.render(getCarModel(make, request),csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing class name to delete & make from delete class view
    public CompletionStage<Result> postDeleteClass(Http.Request request){
        JsonNode make_class_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/data/car-models/delete")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(make_class_json)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });

    }

    // Renders car model details view
    public Result getCarModelDetailsView(String make, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        return ok(views.html.car_model.car_model_details.render(getCarModel(make, request),csrfToken,getRole(request)));
    }
}
