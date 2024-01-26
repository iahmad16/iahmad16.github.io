package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.inject.Inject;
import models.classes.*;
import play.filters.csrf.CSRF;
import play.libs.Json;
import play.mvc.*;
import play.libs.ws.*;

import static play.mvc.Results.ok;
import static models.enums.AdminRole.*;
import static controllers.Helper.*;

@CheckWATExpiration
public class SuperAdminController {

    private static WSClient ws;

    @Inject
    public SuperAdminController(WSClient ws) {
        this.ws = ws;
    }

    // CREATE ADMIN METHODS
    // Renders create admin view
    public Result getCreateAdminView(Http.Request request) {

        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);
        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.admin.create_admin.render(csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing inputted information from create admin view
    public CompletionStage<Result> postCreateAdminView(Http.Request request) {
        JsonNode admin_input_json = request.body().asJson();
        String role_input = admin_input_json.get("role").asText();

        // Parse JSON to ObjectNode
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode modifiedNode = objectMapper.convertValue(admin_input_json, ObjectNode.class);
        modifiedNode.put("role", toAdminRole(role_input).ordinal());
        JsonNode convertedNode = objectMapper.convertValue(modifiedNode,JsonNode.class);

        String WAT = request.session().get("WAT").orElse("None");
        return ws.url("http://161.35.78.39:9000/admins/accounts/create")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(convertedNode)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });

    }

    // ADMIN METHODS
    // Retrieves admin list from getAdminListings and returns the Admin with the specified uid
    public Admin getAdmin(String uid, Http.Request request){
        List<Admin> adminList = getAdminListings(request);
        return adminList.stream().filter(admin -> uid.equals(admin.uid)).findFirst().orElse(null);
    }

    // Sends GET request to API, fetches the Admin List, maps nodes to objects and returns list of Admin objects
    public static List<Admin> getAdminListings(Http.Request request){

        String WAT = request.session().get("WAT").orElse("None");
        return ws.url("http://161.35.78.39:9000/admins/accounts")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .get()
                .thenApply(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Admin> adminList = new ArrayList<>();
                        List<JsonNode> adminNodes = objectMapper.readerForListOf(JsonNode.class).readValue(response.asJson().get("SUCCESS"));
                        adminNodes.forEach(amn -> {
                            Admin admin = new Admin(amn.get("uid").asText(),
                                    amn.get("joinDate").asLong(),
                                    amn.get("name").asText(),
                                    amn.get("emailAddress").asText(),
                                    amn.get("accountType").asText(),
                                    amn.get("role").asText(),
                                    amn.get("status").asText()
                            );
                            adminList.add(admin);
                        });
                        return adminList;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toCompletableFuture().join();
    }

    // Retrieves admin list from getAdminListings and renders admin listings view
    public Result getAdminListingsView(Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.admin.admin_listings.render(csrfToken,getAdminListings(request),getRole(request)));
    }

    // Retrieves admin from getAdmin and renders admin details view
    public Result getAdminDetailsView(String uid, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.admin.admin_details.render(csrfToken, getAdmin(uid, request), getRole(request)));
    }

    // Retrieves admin from getAdmin and renders edit admin details view
    public Result getEditAdminDetailsView(String uid, Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);
        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.admin.edit_admin.render(csrfToken,getAdmin(uid,request),getRole(request)));
    }

    // Sends POST request to API with a JSON object containing inputted updated admin
    // information from edit admin view
    public CompletionStage<Result> postEditAdminDetails(Http.Request request){
        JsonNode updated_admin_json = request.body().asJson();
        String updated_uid = updated_admin_json.get("uid").asText();
        String updated_name = updated_admin_json.get("name").asText();
        String updated_email = updated_admin_json.get("emailAddress").asText();
        String updated_role = updated_admin_json.get("role").asText();

        JsonNode updated_admin_modified = Json.newObject()
                .put("uid",updated_uid)
                .put("name", updated_name)
                .put("emailAddress", updated_email)
                .put("role",toAdminRole(updated_role).toString());

        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/accounts/update")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(updated_admin_modified)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

    // Sends POST request to API with JSON object with uid of admin to delete
    public CompletionStage<Result> postDeactivateAdmin(Http.Request request){
        JsonNode uid_input_json = request.body().asJson();
        Optional<String> WAT = request.session().get("WAT");

        return ws.url("http://161.35.78.39:9000/admins/accounts/delete")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT.orElse("None"))
                .post(uid_input_json)
                .thenApply(response -> {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });

    }


    // CUSTOMER METHODS
    // Retrieves customer list from getCustomerListings and returns the customer with the specified uid
    public static Customer getCustomer(String uid, Http.Request request){
        List<Customer> customerList = getCustomerListings(request);
        return customerList.stream().filter(customer -> uid.equals(customer.uid)).findFirst().orElse(null);
    }

    // Sends GET request to API, fetches the Customer List, maps nodes to objects and returns list of Customer objects
    public static List<Customer> getCustomerListings(Http.Request request){
        String WAT = request.session().get("WAT").orElse("None");
        return ws.url("http://161.35.78.39:9000/admins/customers")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .get()
                .thenApply(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Customer> customerList = new ArrayList<>();
                        List<JsonNode> customerNodes = objectMapper.readerForListOf(JsonNode.class).readValue(response.asJson().get("SUCCESS"));

                        customerNodes.forEach(customerNode -> {
                            List<String> carListingsIDList = objectMapper.convertValue(customerNode.get("carListingsIDs"), List.class);
                            Customer customer = new Customer(customerNode.get("uid").asText(),
                                    customerNode.get("joinDate").asLong(),
                                    customerNode.get("accountType").asText(),
                                    customerNode.get("accountStatus").asText(),
                                    customerNode.get("phoneNumber").asText(),
                                    customerNode.get("emailAddress").asText(),
                                    customerNode.get("firstName").asText(),
                                    customerNode.get("lastName").asText(),
                                    carListingsIDList
                            );
                            customerList.add(customer);
                        });
                        return customerList;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toCompletableFuture().join();
    }

    // Retrieves customer list from getCustomerListings and renders customer listings view
    public Result getCustomerListingsView(Http.Request request){
        return ok(views.html.customer.customer_listings.render(getCustomerListings(request),getRole(request)));
    }

    // Retrieves customer from getCustomer and renders customer details view
    public Result getCustomerDetailsView(String uid, Http.Request request){
        return ok(views.html.customer.customer_details.render(getCustomer(uid,request), getRole(request)));
    }
}
