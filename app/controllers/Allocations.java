/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import models.Allocation;
import models.CourseSession;
import models.Instructor;
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

public class Allocations {
    
    private static final Logger.ALogger appLogger = Logger.of("application");
    
    public static Result list() {  
        try {
            List<Allocation> objects = Allocation.getList();
            JsonNode jsonObjects = Allocation.jsonListSerialization(objects);
            if (jsonObjects.isArray()) {
                int i = 0;
                for (JsonNode object : jsonObjects) {
                    ((ObjectNode)object).put("courseSession",objects.get(i).getCourseSession().jsonSerialization());
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
            Allocation object = Allocation.findByPropertie("id", id);
            JsonNode jsonObject = object.jsonSerialization();
            return ok(jsonObject);
        } catch (Exception e) {
            appLogger.error("Error getting object",e);
            return notFound("Error getting object"); 
        }
    }
    
    public static Result getByInstructor(Long instructorId) {  
        try {
            Allocation object = Allocation.findByPropertie("instructor_id", instructorId);
            JsonNode jsonObject = object.jsonSerialization();
            return ok(jsonObject);
        } catch (Exception e) {
            appLogger.error("Error getting object",e);
            return notFound("Error getting object"); 
        }
    }
    
    public static Result create() {
        // Get the form from the request
        Form<Allocation> form = Form.form(Allocation.class,Allocation.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        Allocation object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            Allocation.create(object);
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
        Form<Allocation> form = Form.form(Allocation.class,Allocation.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        Allocation object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            Allocation.update(object);
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
        Allocation object;
        // Find the object
        try {
            object = Allocation.findByPropertie("id", id);
        } catch (Exception e) {
            return notFound("Error deleting object. Object not found");
        }
        // Delete the object from db
        Ebean.beginTransaction();
        try {
            Allocation.delete(object);
            Ebean.commitTransaction();
            return ok();            
        } catch (Exception e) {
            appLogger.error("Error deleting object", e);
            return internalServerError("Error deleting object");
        } finally {
            Ebean.endTransaction();
        }
    }
    
    public static Result allocateSeats() {
        try {
            // Get the form from the request
            Form<Allocation> form = Form.form(Allocation.class,Allocation.creation.class).bindFromRequest();
            // Validate errors
            if(form.hasErrors()) {
                return badRequest(form.errorsAsJson());
            }
            // Get the object from the form
            Allocation object = form.get();
            Allocation newAllocation = object;
            
            boolean bExists = false;
            for (Allocation allocation : Allocation.getList())
            {
                if (allocation.getInstructor().getId().equals(object.getInstructor().getId())
                        && allocation.getCourseSession().getId().equals(object.getCourseSession().getId()))
                {
                    bExists = true;
                    newAllocation = allocation;
                }
            }
            
            if (!bExists)
            {
                Instructor instructor = newAllocation.getInstructor();
                CourseSession courseSession = newAllocation.getCourseSession();
                Long capacity = newAllocation.getCapacity();
                instructor.getAllocations().add(object);
                Instructor.update(instructor);
                courseSession.getAllocations().add(object);
                courseSession.setTotalCapacity(courseSession.getTotalCapacity() + capacity);
                CourseSession.update(courseSession);
                Long availableSeats = courseSession.getTotalCapacity() - courseSession.getCurrentAllocation();
                Allocation.create(object);
                JsonNode jsonObject = object.jsonSerialization();
                return created(jsonObject);
            }
            else
            {
                CourseSession courseSession = newAllocation.getCourseSession();
                Long capacityDiff = newAllocation.getCapacity() - object.getCapacity();
                Long availableSeats = courseSession.getTotalCapacity() - courseSession.getCurrentAllocation();
                if (availableSeats < capacityDiff)
                {
                    newAllocation.setCapacity(newAllocation.getCapacity() - availableSeats);
                    courseSession.setTotalCapacity(courseSession.getTotalCapacity() - availableSeats);
                }
                else
                {
                    newAllocation.setCapacity(object.getCapacity());
                    courseSession.setTotalCapacity(courseSession.getTotalCapacity() + Math.abs(capacityDiff));
                }
                Allocation.update(newAllocation);
                JsonNode jsonObject = newAllocation.jsonSerialization();
                return created(jsonObject);
            }
        } catch (Exception e) {
            appLogger.error(Messages.get("Error creating object"), e);
            return internalServerError("Error creating object");
        }
    }
    
}