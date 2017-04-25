/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import models.Instructor;
import models.Program;
import models.Role;
import models.Student;
import play.Logger;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Result;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

/**
 *
 * @author ejesposito
 */

public class Programs {
    
    private static final Logger.ALogger appLogger = Logger.of("application"); 
    
    public static Result get(Long id) {  
        try {
            Program object = Program.findByPropertie("id", id);
            
            List<Instructor> instructors = Instructor.getInstructorsList();
            List<Student> students = Student.getStudentsList();
            
            JsonNode jsonObject = object.jsonSerialization();
            return ok(jsonObject);
        } catch (Exception e) {
            appLogger.error("Error getting object",e);
            return notFound("Error getting object"); 
        }
    }
    
    public static Result update(Long id) {
        // Get the form from the request
        Form<Program> form = Form.form(Program.class,Program.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        Program object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            Program.update(object);
            JsonNode jsonObject = object.jsonSerialization();
            Ebean.commitTransaction();
            return ok(jsonObject);
        } catch (Exception e) {
            appLogger.error(Messages.get("Error updating object"), e);
            return internalServerError("Error updating object");
        } finally {
            Ebean.endTransaction();
        }
    }
    
}