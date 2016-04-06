package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.authentication.login;

/**
 * Created by Dan on 3/20/2016.
 */
public class SessionController extends Controller {

    // page for new session (login)
    public Result newSession() {
        Form<User> loginForm = Form.form(User.class);
        return ok(login.render(loginForm));
    }

    // create new session (login)
    public Result create() {
        Form<User> loginForm = Form.form(User.class).bindFromRequest();
        String name = loginForm.field("full_name").value();
        String acc = loginForm.field("access").value();
        if (name == null || acc == null) {
            return badRequest(login.render(loginForm));
        }
        else {
            // store in static class, not db
            User.setEmail(loginForm.field("email").value());
            User.setFull_name(name);
            User.setAccess(acc);
            return redirect(routes.CaseController.list());
        }
    }

    // logging out
    public Result destroy() {
        User.logout();
        return redirect(routes.SessionController.newSession());
    }
}
