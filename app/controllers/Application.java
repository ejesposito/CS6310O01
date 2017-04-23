package controllers;

import play.mvc.*;
import static play.mvc.Results.ok;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(main.render("University online program"));
    }

    public static Result administratorView() {
        return ok(administrator.render());
    }

    public static Result instructorView() {
        return ok(instructor.render());
    }

    public static Result studentView() {
        return ok(student.render());
    }
    
}
