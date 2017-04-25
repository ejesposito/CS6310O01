/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import models.Administrator;
import models.Administrator;
import models.Instructor;
import models.Instructor;
import models.Person;
import models.Role;
import models.Student;
import models.Student;
import play.Logger;
import play.data.Form;
import play.i18n.Messages;
import static play.mvc.Controller.request;
import play.mvc.Result;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.created;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

/**
 *
 * @author ejesposito
 */

public class Persons {
    
    private static final Logger.ALogger appLogger = Logger.of("application");
    
    public static Result list() {
        try {
            List<Person> objects = Person.getList();
            JsonNode jsonObjects = Person.jsonListSerialization(objects);
            return ok(jsonObjects);
        }catch(Exception e) {
            appLogger.error("Error listing objects",e);
            return internalServerError("Error listing objects"); 
        }
    }     
    
    public static Result get(Long id) {  
        try {
            Person object = Person.findByPropertie("id", id);
            JsonNode jsonObject = object.jsonSerialization();
            return ok(jsonObject);
        } catch (Exception e) {
            appLogger.error("Error getting object",e);
            return notFound("Error getting object"); 
        }
    }
    
    public static Result create() {
        // Get the form from the request
        Form<Person> form = Form.form(Person.class,Person.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        Person object = form.get();
        // Add the roles
        models.Student student = null;
        models.Instructor instructor = null;
        models.Administrator administrator = null;
        JsonNode json = request().body().asJson();
        if (json.get("isAdministrator") != null && json.get("isAdministrator").asBoolean() == true) {
            object.getRoles().add(new Administrator(null, object));
        }
        if (json.get("isInstructor") != null && json.get("isInstructor").asBoolean() == true) {
            object.getRoles().add(new Instructor(null, object));
        }
        if (json.get("isStudent") != null && json.get("isStudent").asBoolean() == true) {
            object.getRoles().add(new Student(null, object));
        }
        // Create the object in db
        Ebean.beginTransaction();
        try {
            Person.create(object);
            if (administrator != null)
                Administrator.create(administrator);
            if (instructor != null)
                Instructor.create(instructor);
            if (student != null)
                Student.create(student);
            JsonNode jsonObject = object.jsonSerialization();
            Ebean.commitTransaction();
            return created(jsonObject);
        } catch (Exception e) {
            appLogger.error(Messages.get("Error creating object"), e);
            return internalServerError("Error creating object");
        } finally {
            Ebean.endTransaction();
        }
    }
    
    public static Result update(Long id) {
        // Get the form from the request
        Form<Person> form = Form.form(Person.class,Person.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        Person object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            Person.update(object);
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
    
    public static Result delete(Long id) {
        Person object;
        // Find the object
        try {
            object = Person.findByPropertie("id", id);
        } catch (Exception e) {
            return notFound("Error deleting object. Object not found");
        }
        // Delete the object from db
        Ebean.beginTransaction();
        try {
            for (Role r : object.getRoles()) {
                Role.delete(r);
            }
            Person.delete(object);
            Ebean.commitTransaction();
            return ok();            
        } catch (Exception e) {
            appLogger.error("Error deleting object", e);
            return internalServerError("Error deleting object");
        } finally {
            Ebean.endTransaction();
        }
    }
    
}