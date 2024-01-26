package controllers;


import play.mvc.*;


public class WebsiteController extends Controller {
    // Home Page
    public Result getWebsiteHomeView(){
        return ok(views.html.website.website_home.render());
    }
}
