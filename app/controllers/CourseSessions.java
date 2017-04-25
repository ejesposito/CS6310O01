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
import models.CourseSession;
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

public class CourseSessions {
    
    private static final Logger.ALogger appLogger = Logger.of("application");


    public static Result requestCourse(int courseId, int studentId) {
        try {
            CourseSession courseSession = CourseSession.findByPropertie("id", courseId);
            Student student = Student.findByPropertie("id", studentId);
            if (student.hasPrereqsForCourse(courseSession.getCourse())) {
                if (courseSession.hasCourseRoom()) {
                    courseSession.setCurrentAllocation(courseSession.getCurrentAllocation() - 1);
                    CourseSession.update(courseSession);
                    student.getProgramCourses().add(courseSession.getCourse());
                    Student.update(student);
                    return ok("Enrolled");
                } else {
                    return ok("Not enough room; Waitlisted");
                }
             } else {
                return ok("Student does not meet pre-reqs");
            }
        } catch (Exception e) {
            appLogger.error("Error listing objects",e);
            return internalServerError("Error listing objects");
        }
    }
    public static Result list() {  
        try {
            List<CourseSession> objects = CourseSession.getList();
            JsonNode jsonObjects = CourseSession.jsonListSerialization(objects);
            if (jsonObjects.isArray()) {
                int i = 0;
                for (JsonNode object : jsonObjects) {
                    ((ObjectNode)object).put("course",objects.get(i).getCourse().jsonSerialization());
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
            CourseSession object = CourseSession.findByPropertie("id", id);
            JsonNode jsonObject = object.jsonSerialization();
            return ok(jsonObject);
        } catch (Exception e) {
            appLogger.error("Error getting object",e);
            return notFound("Error getting object"); 
        }
    }
    
    public static Result create() {
        // Get the form from the request
        Form<CourseSession> form = Form.form(CourseSession.class,CourseSession.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        CourseSession object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            CourseSession.create(object);
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
        Form<CourseSession> form = Form.form(CourseSession.class,CourseSession.creation.class).bindFromRequest();
        // Validate errors
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        // Get the object from the form
        CourseSession object = form.get();
        // Create the object in db
        Ebean.beginTransaction();
        try {
            CourseSession.update(object);
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
        CourseSession object;
        // Find the object
        try {
            object = CourseSession.findByPropertie("id", id);
        } catch (Exception e) {
            return notFound("Error deleting object. Object not found");
        }
        // Delete the object from db
        Ebean.beginTransaction();
        try {
            CourseSession.delete(object);
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