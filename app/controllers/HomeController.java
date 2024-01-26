package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import play.filters.csrf.CSRF;
import play.libs.ws.*;
import play.mvc.*;
import static controllers.Helper.*;

@CheckWATExpiration
public class HomeController extends Controller {
    private final WSClient ws;

    @Inject
    public HomeController(WSClient ws) {
        this.ws = ws;
    }

    // Renders the index view
    public Result getHomeView(Http.Request request) {

        Integer numCars = CarController.getCarListings(request).size();
        Integer numShops = ShopController.getShopListings(request).size();
        Integer numCustomers = SuperAdminController.getCustomerListings(request).size();

        return ok(views.html.index.render(numCars, numShops, numCustomers, getRole(request)));
    }

    // Renders the change password view
    public Result getChangePasswordView(Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.change_password.render(csrfToken,getRole(request)));
    }

    // Sends POST request to API with a JSON object containing new inputted password
    // from change password view
    public CompletionStage<Result> postChangePasswordView(Http.Request request) {

        JsonNode password_input_json = request.body().asJson();
        String WAT = request.session().get("WAT").orElse("None");

        return ws.url("http://161.35.78.39:9000/admins/accounts/update/password")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(password_input_json)
                .thenApply(response ->  {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }

}
