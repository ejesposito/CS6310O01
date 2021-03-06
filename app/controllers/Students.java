/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.List;
import models.Student;
import play.Logger;
import play.data.Form;
import play.i18n.Messages;
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

public class Students {
    
    private static final Logger.ALogger appLogger = Logger.of("application");
    
    public static Result list() {  
        try {  
            List<Student> objects = Student.getStudentsList();
            JsonNode jsonObjects = Student.jsonStudentsListSerialization(objects);
            if (jsonObjects.isArray()) {
                int i = 0;
                for (JsonNode object : jsonObjects) {
                    ((ObjectNode)object).put("person",objects.get(i).getPerson().jsonSerialization());
                    i++;
                }
            }
            return ok(jsonObjects);
        }catch(Exception e) {
            appLogger.error("Error listing objects",e);
            return internalServerError("Error listing objects"); 
        }
    }     
    
    public static Result get(Long id) {  
        try {
            Student object = Student.findByPropertie("id", id);
            JsonNode jsonObject = object.jsonSerialization();
            return ok(jsonObject);
        } catch (Exception e) {
            appLogger.error("Error getting object",e);
            return notFound("Error getting object"); 
        }
    }
    
    public static Result create() {
        // Get the form from the request
        Form<Student> form = Form.form(Student.class,Student.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        Student object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            Student.create(object);
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
        Form<Student> form = Form.form(Student.class,Student.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        Student object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            Student.update(object);
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
        Student object;
        // Find the object
        try {
            object = Student.findByPropertie("id", id);
        } catch (Exception e) {
            return notFound("Error deleting object. Object not found");
        }
        // Delete the object from db
        Ebean.beginTransaction();
        try {
            Student.delete(object);
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