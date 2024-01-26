package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static models.enums.AccountType.*;
import static models.enums.Status.*;

@CheckWATExpiration
public class ShopController extends Controller {
    private static WSClient ws;

    @Inject
    public ShopController(WSClient ws) {
        this.ws = ws;
    }

    // Sends GET request to API to get access to the shop's logo image
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

    // Returns the Shop with the specified uid.
    public Shop getShop(Http.Request request, String uid){
        List<Shop> shopList = getShopListings(request);
        return shopList.stream().filter(shop -> uid.equals(shop.uid)).findFirst().orElse(null);
    }

    // Sends GET request to API, fetches the Shop List, maps nodes to Objects and returns list of Shop Objects
    public static List<Shop> getShopListings(Http.Request request){

        String WAT = request.session().get("WAT").orElse("None");
        return ws.url("http://161.35.78.39:9000/admins/shops")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .get()
                .thenApply(response -> {
                    try
                    {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Shop> shopList = new ArrayList<>();
                        List<JsonNode> shop_list = objectMapper.readerForListOf(JsonNode.class).readValue(response.asJson().get("SUCCESS"));
                        List<JsonNode> active_shop_list = shop_list.stream().filter(shop -> shop.get("status").asText().equals("ACTIVE")).collect(Collectors.toList());
                        for (JsonNode shop_json : active_shop_list) {
                            List<SocialMediaAccount> socialMediaAccountsList = parseSocialMediaAccounts(shop_json.get("socialMediaAccounts"));
                            List<DiscountedService> discountedServicesList = parseDiscountedServices(shop_json.get("discountedServices"));

                            Shop shop = new Shop(
                                    shop_json.get("uid").asText(),
                                    shop_json.get("joinDate").asLong(),
                                    stringtoStatus(shop_json.get("status").asText()),
                                    toAccountType(shop_json.get("accountType").asText()),
                                    shop_json.get("emailAddress").asText(),
                                    shop_json.get("name").asText(),
                                    shop_json.get("phoneNumber").asText(),
                                    shop_json.get("address").asText(),
                                    getImage(shop_json.get("logo").asText(),WAT),
                                    new Location(shop_json.get("location").get("latitude").asDouble(),shop_json.get("location").get("longitude").asDouble()),
                                    socialMediaAccountsList,
                                    discountedServicesList
                            );
                            shopList.add(shop);
                        }
                        return shopList;
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }).toCompletableFuture().join();
    }

    // Retrieves shop list from getShopListings and renders shop listings view
    public Result getShopListingsView(Http.Request request) {
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        List<Shop> activeShopsList = getShopListings(request).stream().filter(shop -> shop.status.equals(ACTIVE)).collect(Collectors.toList());
        return ok(views.html.shop.shop_listings.render(csrfToken,activeShopsList,getRole(request)));
    }

    // Retrieves shop from getShop and renders shop details view
    public Result getShopDetailsView(String uid, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.shop.shop_details.render(csrfToken,getShop(request,uid), getRole(request)));
    }

    // Renders add shop view
    public Result getAddShopView(Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        return ok(views.html.shop.add_shop.render(csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing inputted information from add shop view
    public CompletionStage<Result> postAddShopView(Http.Request request) {
        JsonNode shop_input_json = request.body().asJson();
        String shop_name = shop_input_json.get("name").asText();
        String shop_email = shop_input_json.get("email").asText();
        String shop_address = shop_input_json.get("address").asText();
        String shop_phone = shop_input_json.get("phone").asText();
        String shop_logo = shop_input_json.get("logo").asText();
        Double shop_latitude = shop_input_json.get("latitude").asDouble();
        Double shop_longitude = shop_input_json.get("longitude").asDouble();
        JsonNode socialMediaAccountsNode = shop_input_json.get("socialMediaAccounts");
        JsonNode discountedServicesNode = shop_input_json.get("discountedServices");
        Location shop_location = new Location(shop_latitude,shop_longitude);

        JsonNode shop_input_data_modified = Json.newObject()
                .put("emailAddress", shop_email)
                .put("name", shop_name)
                .put("phoneNumber",shop_phone)
                .put("address",shop_address)
                .put("logo",shop_logo)
                .putPOJO("location", shop_location)
                .putPOJO("socialMediaAccounts", parseSocialMediaAccounts2(socialMediaAccountsNode))
                .putPOJO("discountedServices", parseDiscountedServices2(discountedServicesNode));

        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admin/admins/shops/create")
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

    // Renders edit shop view
    public Result getEditShopDetailsView(String uid, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        return ok(views.html.shop.edit_shop.render(getShop(request,uid),csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing updated information from edit shop view
    public CompletionStage<Result> postEditShopDetails(Http.Request request) {

        JsonNode shop_input_json = request.body().asJson();
        String shop_uid = shop_input_json.get("uid").asText();
        String shop_name = shop_input_json.get("name").asText();
        String shop_email = shop_input_json.get("email").asText();
        String shop_address = shop_input_json.get("address").asText();
        String shop_phone = shop_input_json.get("phone").asText();
        String shop_logo = shop_input_json.get("logoURL").asText();
        Double shop_latitude = shop_input_json.get("latitude").asDouble();
        Double shop_longitude = shop_input_json.get("longitude").asDouble();
        JsonNode socialMediaAccountsNode = shop_input_json.get("socialMediaAccounts");
        JsonNode discountedServicesNode = shop_input_json.get("discountedServices");
        Location shop_location = new Location(shop_latitude,shop_longitude);

        JsonNode shop_input_data_modified = Json.newObject()
                .put("uid", shop_uid)
                .put("emailAddress", shop_email)
                .put("name", shop_name)
                .put("phoneNumber",shop_phone)
                .put("address",shop_address)
                .put("logo",shop_logo)
                .putPOJO("location", shop_location)
                .putPOJO("socialMediaAccounts", parseSocialMediaAccounts2(socialMediaAccountsNode))
                .putPOJO("discountedServices", parseDiscountedServices2(discountedServicesNode));

        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/shops/update")
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

    // Sends POST request to API with a JSON object containing uid of the shop to be deleted
    public CompletionStage<Result> postDeleteShop(Http.Request request){
        JsonNode uid_input_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/shops/delete")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(uid_input_json)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }
}
