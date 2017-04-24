package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Administrator;
import models.Course;
import models.CourseSession;
import models.Instructor;
import models.Person;
import models.Program;
import models.Program.Term;
import models.Student;
import play.mvc.*;
import static play.mvc.Results.ok;

import views.html.*;

public class Application extends Controller {
    
    private static final play.Logger.ALogger appLogger = play.Logger.of("application");

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
    
     public static Result loadCSVData() {
        // Parsing and creation off all the objects in RAM
        String studentsCSVFilePath = "students.csv";
        String instructorsCSVFilePath = "instructors.csv";
        String coursesCSVFilePath = "courses.csv";
        String coursesSessionsCSVFilePath = "terms.csv";
        String prereqsCSVFilePath = "prereqs.csv";
        String token = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"; 
        models.Program program;
        try {
            program = Program.findByPropertie("id", 1);
            studentsCSVtoProgram(program, studentsCSVFilePath, token);
            instructorsCSVtoProgram(program, instructorsCSVFilePath, token);
            coursesCSVtoProgram(program, coursesCSVFilePath, token);
            coursesSessionsCSVtoProgram(program, coursesSessionsCSVFilePath, token);
            prereqsCSVtoProgram(program, prereqsCSVFilePath, token);
        } catch (Exception e) {
            appLogger.error("Error finding program or loading CSV data", e);
            return internalServerError("Error finding program or loading CSV data");
        }
        return ok();
    }
    
    public interface ObjectParser{
        public void parse (Program program, String[] data);
    }

    public static void CSVtoProgram (Program program, String filePath, String token, ObjectParser parser) throws Exception {
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(token);
                parser.parse(program, data);
            }
        } catch (FileNotFoundException e) {
            appLogger.error("File not found", e);
            throw e;
        } catch (IOException e) {
            appLogger.error("IO Exception", e);
            throw e;
        }
    }
    
    public static void studentsCSVtoProgram (Program program, String filePath, String token) throws Exception {
        CSVtoProgram (program, filePath, token, new ObjectParser() {
            @Override
            public void parse(Program program, String[] data) {
                if (data.length == 4) {
                    try {
                        // Parse
                        Long uuid = Long.parseLong(data[0]);
                        String name = data[1];
                        String address = data[2];
                        Long number = Long.parseLong(data[3]);
                        // Search the person in the db
                        Person person = Person.findByPropertie("name", name);
                        if (person != null) {
                            // Create and add student role
                            person.getRoles().add(new Student(null, person));
                            // Save the person
                            Person.update(person);
                        } else {
                            // Create the person
                            person = new Person (uuid, name, address, number.toString());
                            // Create and add student role
                            person.getRoles().add(new Student(null, person));
                            // Save the person
                            Person.create(person);
                        }
                    } catch (Exception ex) {
                        appLogger.error("Error parsing an student", ex);
                    }
                }
            }
        });
    }
    
    public static void instructorsCSVtoProgram (Program program, String filePath, String token) throws Exception {
        CSVtoProgram (program, filePath, token, new ObjectParser() {
            @Override
            public void parse(Program program, String[] data) {
                if (data.length == 4) {
                    try {
                        Long uuid = Long.parseLong(data[0]);
                        String name = data[1];
                        String address = data[2];
                        Long number = Long.parseLong(data[3]);
                        // Search the person in the db
                        Person person = Person.findByPropertie("name", name);
                        if (person != null) {
                            // Create and add instructor role
                            person.getRoles().add(new Instructor(null, person));
                            // Save the person
                            Person.update(person);
                        } else {
                            // Create the person
                            person = new Person (uuid, name, address, number.toString());
                            // Create and add instructor role
                            person.getRoles().add(new Instructor(null, person));
                            // Save the person
                            Person.create(person);
                        }
                    } catch (Exception e) {
                        appLogger.error("Error parsing an instructor", e);
                    }
                }
            }
        });
    }
    
    public static void coursesCSVtoProgram (Program program, String filePath, String token) throws Exception {
        CSVtoProgram (program, filePath, token, new ObjectParser() {
            @Override
            public void parse(Program program, String[] data) {
                if (data.length == 2) {
                    try {
                        Long uuid = Long.parseLong(data[0]);
                        String title = data[1];
                        Course course = Course.findByPropertie("title", title);
                        if (course == null) {
                            // Create the course
                            course = new Course (uuid, title);
                            Course.create(course);
                        }
                    } catch (Exception e) {
                        appLogger.error("Error parsing an course", e);
                    }
                }
            }
        }); 
    }
    
    public static void coursesSessionsCSVtoProgram (Program program, String filePath, String token) throws Exception {
        CSVtoProgram (program, filePath, token, new ObjectParser() {
            @Override
            public void parse(Program program, String[] data) {
                if (data.length == 2) {
                    try {
                        Long uuid = Long.parseLong(data[0]);
                        Term term = Term.valueOf(data[1].toUpperCase());
                        CourseSession courseSession = CourseSession.findByProperties
                                (Arrays.asList("course.id", "session_term"), Arrays.asList(uuid, term));
                        Course course = Course.findByPropertie("uuid", uuid);
                        if (courseSession == null && course != null) {
                            // Create the course session
                            courseSession = new CourseSession (program.getCurrentYear(), term, course);
                            CourseSession.create(courseSession);
                        } 
                    } catch (Exception e) {
                        appLogger.error("Error parsing an course term", e);
                    }
                }
            }
        }); 
    }
    
    public static void prereqsCSVtoProgram (Program program, String filePath, String token) throws Exception {
        CSVtoProgram (program, filePath, token, new ObjectParser() {
            @Override
            public void parse(Program program, String[] data) {
                if (data.length == 2) {
                    try {
                        Long prerequisitUUID = Long.parseLong(data[0]);
                        Long courseUUID = Long.parseLong(data[1]);
                        Course course = Course.findByPropertie("uuid", courseUUID);
                        Course prerequisit = Course.findByPropertie("uuid", prerequisitUUID);
                        if (course != null && prerequisit != null) {
                            // Add the prerequisit to the course
                            course.getPrerequisites().add(prerequisit);
                            Course.update(course);
                        } 
                    } catch (Exception e) {
                        appLogger.error("Error parsing an course term", e);
                    }
                }
            }
        }); 
    }
    
}
