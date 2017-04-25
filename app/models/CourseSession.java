package models;

import com.avaje.ebean.ExpressionList;
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
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import models.Program.Term;
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
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "courseSession")
    private List<Allocation> allocations;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    @JsonBackReference
    private Course course;
    
    @ManyToMany
    @JoinTable(name = "courses_sessions_waiting", joinColumns = {
        @JoinColumn(name = "course_session_id")}, inverseJoinColumns = {
        @JoinColumn(name = "student_id")})
    private List<Course> waitingList;
    
    private static final Finder<Long, CourseSession> finder = new Finder<>(Long.class, CourseSession.class);

    public CourseSession () {
        
    }
    
    public CourseSession (Long sessionYear, Term sessionTerm, Course course) {
        this.sessionYear = sessionYear;
        this.sessionTerm = sessionTerm;
        this.course = course;
    }
    
    public boolean hasCourseRoom() {

        if (totalCapacity == null || currentAllocation == null) {
            if (totalCapacity == null) {
                this.totalCapacity = Long.getLong("1");
            }
            if (currentAllocation == null) {
                this.currentAllocation = Long.getLong("1");
            }
            System.out.println(totalCapacity);
            System.out.println(currentAllocation);
            return true;
        }
        if (totalCapacity < currentAllocation) {
            return true;
        } else {
            return false;
        }
    }

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
    
    /**
     * This method search in the database looking for a unique object using the
     * key and the value.
     * @param keys
     * @param values
     * @return dAttributePermission
     * @throws java.lang.Exception 
     */
    public static CourseSession findByProperties(List<String> keys, List<Object> values)  
        throws Exception {
        
        ExpressionList<CourseSession> expList = finder.where();
            
        for(int i = 0; i < keys.size(); i++){
            expList.eq(keys.get(i), values.get(i));
        }
        return expList.findUnique();         
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
     * @return the allocations
     */
    public List<Allocation> getAllocations() {
        return allocations;
    }

    /**
     * @param allocations the allocations to set
     */
    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
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
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }
    
    /**
     * @return the waitingList
     */
    public List<Course> getWaitingList() {
        return waitingList;
    }

    /**
     * @param waitingList the waitingList to set
     */
    public void setWaitingList(List<Course> waitingList) {
        this.waitingList = waitingList;
    }
    
}
