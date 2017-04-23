package models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.Entity;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends models.Role 
{
    public interface creation{}
    
    @ManyToMany(mappedBy = "instructors")
    private List<CourseSession> coursesSessions;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "instructor")
    private List<Capacity> capacities;
    
    private static final Finder<Long, Instructor> finder = new Finder<>(Long.class, Instructor.class);
    
    public Instructor () {
        
    }
    
    public Instructor (Long id) {
        super(id, "INSTRUCTOR");
    }
    
    public Instructor (Long id, Person person) {
        super(id, "INSTRUCTOR", person);
    }
    
    public static void create (Instructor object) throws Exception       
    {
        object.save();
    }
    
    public static void update (Instructor object) throws Exception
    {
        object.update();
    }
    
    public static void delete (Instructor object) throws Exception
    {
        object.delete();
    }
    
    public static Instructor findByPropertie(String key,Object obj) throws Exception
    {
        return finder.where().eq(key,obj).findUnique();
    }
    
    public static List<Instructor> getInstructorsList () throws Exception
    {
        return finder.all();
    }
    
   /**
     * Serialize all the class attributes
     * @return
     * @throws Exception 
     */
    @Override
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
    public static JsonNode jsonInstructorsListSerialization(List<Instructor> objects) throws Exception
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
    public static Instructor jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        Instructor object;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,Instructor.class);
        return object;
    }
    
    /**
     * @return the coursesSessions
     */
    public List<CourseSession> getCoursesSessions() {
        return coursesSessions;
    }

    /**
     * @param coursesSessions the coursesSessions to set
     */
    public void setCoursesSessions(List<CourseSession> coursesSessions) {
        this.coursesSessions = coursesSessions;
    }

    /**
     * @return the capacities
     */
    public List<Capacity> getCapacities() {
        return capacities;
    }

    /**
     * @param capacities the capacities to set
     */
    public void setCapacities(List<Capacity> capacities) {
        this.capacities = capacities;
    }
    
}
