package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.filters.csrf.CSRF;
import play.libs.Json;
import play.libs.ws.*;
import play.mvc.*;
import java.util.*;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;

public class LoginController extends Controller {

    private final WSClient ws;

    @Inject
    public LoginController(WSClient ws) {
        this.ws = ws;
    }

    // Renders login view
    public Result getLoginView(Http.Request request) {

        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.login.login.render(csrfToken)).addingToSession(request,"CSRF",csrfToken.toString());
    }

    // Sends POST request to API with JSON object containing login details
    public CompletionStage<Result> postLogin(Http.Request request) {
        JsonNode body = request.body().asJson();
        String email = body.get("email").asText();
        String password = body.get("password").asText();

        JsonNode login_data = Json.newObject()
                        .put("emailAddress", email)
                        .put("password", password);

        return ws.url("http://161.35.78.39:9000/admins/login")
                .addHeader("Content-Type", "application/json")
                .post(login_data)
                .thenApply(response -> {
                    if (response.getStatus() == 200){
                        String WAT = response.asJson().get("SUCCESS").get("WAT").asText();
                        return ok("Success").addingToSession(request, "WAT", WAT);
                    }
                    return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                });
    }

    // Renders forgot password view
    public CompletionStage<Result> getForgotPasswordView(Http.Request request){

        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        return ws.url("http://161.35.78.39:9000/login")
                .addHeader("Content-Type", "application/json")
                .post(Json.newObject())
                .thenApply(response -> {
                    String WAT2 = response.asJson().get("SUCCESS").get("WAT").asText();
                    if(csrfToken == null)
                    {
                        CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
                        return ok(views.html.login.login.render(defaultToken));
                    }
                    return ok(views.html.login.forgotpassword.render(csrfToken)).addingToSession(request, "WAT2", WAT2);
                });
    }

    // Sends POST request to API with JSON object containing email to receive new password
    public CompletionStage<Result> postForgotPassword(Http.Request request){
        JsonNode body = request.body().asJson();
        String email = body.get("email").asText();
        String WAT = request.session().get("WAT2").orElse("None");

        JsonNode login_data = Json.newObject()
                .put("emailAddress", email);

        return ws.url("http://161.35.78.39:9000/admins/forgot-password")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", WAT)
                .post(login_data)
                .thenApply(response ->  {
                    if(response.getStatus() != 200){
                        return Results.status(response.getStatus(), response.asJson().get("ERROR").get("errorMessage").asText());
                    }
                    return Results.status(response.getStatus(), response.getStatusText());
                });
    }
}
