package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import play.db.ebean.Model;

@Entity
@Table(name="courses_sessions")
public class CourseSession extends Model 
{
    
    public interface creation{}
    
    @Id
    private Long id;
    
    private Long sessionYear;
    
    private Program.Term sessionTerm;
    
    private Long totalCapacity;
    
    private Long currentAllocation;
    
    @ManyToMany
    @JoinColumn(name="instructor_id")
    @JsonBackReference
    private List<Instructor> instructors;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    @JsonBackReference
    private Instructor course;
    
    private static final Finder<Long, CourseSession> finder = new Finder<>(Long.class, CourseSession.class);
    
    public static void create (CourseSession object) throws Exception       
    {
        object.save();
    }
    
    public static void update (CourseSession object) throws Exception
    {
        object.update();
    }
    
    public static void delete (CourseSession object) throws Exception
    {
        object.delete();
    }
    
    public static CourseSession findByPropertie(String key,Object obj) throws Exception
    {
        return finder.where().eq(key,obj).findUnique();
    }
    
    public static List<CourseSession> getList () throws Exception
    {
        return finder.all();
    }
    
   /**
     * Serialize all the class attributes
     * @return
     * @throws Exception 
     */
    public JsonNode jsonSerialization() throws Exception
    {
        JsonNode jsonError;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat);       
        jsonError = mapper.convertValue(this, JsonNode.class);
        return jsonError;
    }
    
   /**
     * Serialize a list of objects with a particular set of attributes
     * @param view
     * @return
     * @throws IOException 
     */
    public JsonNode jsonSerialization(Class view) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        
        ObjectWriter writer = mapper.writerWithView(view);
        JsonNode response = mapper.readTree(writer.writeValueAsString(this));
        return response;
    }
        
   /**
     * Serialize a list of objects with all the class attributes 
     * @param objects
     * @return
     * @throws Exception 
     */
    public static JsonNode jsonListSerialization(List<CourseSession> objects) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        return mapper.convertValue(objects, JsonNode.class);
    }
    
   /**
     * Des-Serialize an object who was in json format
     * @param jsonObject
     * @return
     * @throws Exception 
     */
    public static CourseSession jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        CourseSession object;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,CourseSession.class);
        return object;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the instructors
     */
    public List<Instructor> getInstructors() {
        return instructors;
    }

    /**
     * @param instructors the instructors to set
     */
    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }
 
    /**
     * @return the sessionYear
     */
    public Long getSessionYear() {
        return sessionYear;
    }

    /**
     * @param sessionYear the sessionYear to set
     */
    public void setSessionYear(Long sessionYear) {
        this.sessionYear = sessionYear;
    }

    /**
     * @return the sessionTerm
     */
    public Program.Term getSessionTerm() {
        return sessionTerm;
    }

    /**
     * @param sessionTerm the sessionTerm to set
     */
    public void setSessionTerm(Program.Term sessionTerm) {
        this.sessionTerm = sessionTerm;
    }

    /**
     * @return the totalCapacity
     */
    public Long getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * @param totalCapacity the totalCapacity to set
     */
    public void setTotalCapacity(Long totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    /**
     * @return the currentAllocation
     */
    public Long getCurrentAllocation() {
        return currentAllocation;
    }

    /**
     * @param currentAllocation the currentAllocation to set
     */
    public void setCurrentAllocation(Long currentAllocation) {
        this.currentAllocation = currentAllocation;
    }

    /**
     * @return the course
     */
    public Instructor getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Instructor course) {
        this.course = course;
    }
    
}
