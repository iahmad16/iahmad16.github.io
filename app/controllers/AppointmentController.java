package controllers;


import javax.inject.Inject;
import play.filters.csrf.CSRF;
import play.libs.ws.*;
import play.mvc.*;
import static controllers.Helper.*;

@CheckWATExpiration
public class AppointmentController extends Controller {
    private final WSClient ws;

    @Inject
    public AppointmentController(WSClient ws) {
        this.ws = ws;
    }

    // Renders upcoming appointments view
    public Result getUpcomingAppointmentsView(Http.Request request){
        return ok(views.html.appointment.upcoming_appointments.render(getRole(request)));
    }

    // Renders appointment details view
    public Result getAppointmentDetailsView(Http.Request request) {
        return ok(views.html.appointment.appointment_details.render(getRole(request)));
    }

    // Renders add appointment view
    public Result getAddAppointmentView(Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }

        return ok(views.html.appointment.add_appointment.render(csrfToken,getRole(request)));
    }

    // Renders edit appointment view
    public Result getEditAppointmentDetailsView(Http.Request request){
        CSRF.Token csrfToken = CSRF.getToken(request).orElse(null);

        if(csrfToken == null)
        {
            CSRF.Token defaultToken = new CSRF.Token("defaultTokenName", "defaultTokenValue");
            return ok(views.html.login.login.render(defaultToken));
        }
        return ok(views.html.appointment.edit_appointment.render(csrfToken,getRole(request)));
    }
}
