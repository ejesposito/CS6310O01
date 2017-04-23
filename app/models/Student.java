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
@DiscriminatorValue("STUDENT")
public class Student extends models.Role 
{
    
    public interface creation{}
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "student")
    private List<Record> record;
    
    @ManyToMany
    private List<Course> programCourses;
    
    private static final Finder<Long, Student> finder = new Finder<>(Long.class, Student.class);
    
    public Student () {
        
    }
    
    public Student (Long id) {
        super(id, "STUDENT");
    }
    
    public Student (Long id, Person person) {
        super(id, "STUDENT", person);
    }
    
    public static void create (Student object) throws Exception       
    {
        object.save();
    }
    
    public static void update (Student object) throws Exception
    {
        object.update();
    }
    
    public static void delete (Student object) throws Exception
    {
        object.delete();
    }
    
    public static Student findByPropertie(String key,Object obj) throws Exception
    {
        return finder.where().eq(key,obj).findUnique();
    }
    
    public static List<Student> getStudentsList () throws Exception
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
    public static JsonNode jsonStudentsListSerialization(List<Student> objects) throws Exception
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
    public static Student jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        Student object;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,Student.class);
        return object;
    }
    
    /**
     * @return the record
     */
    public List<Record> getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(List<Record> record) {
        this.record = record;
    }

    /**
     * @return the programCourses
     */
    public List<Course> getProgramCourses() {
        return programCourses;
    }

    /**
     * @param programCourses the programCourses to set
     */
    public void setProgramCourses(List<Course> programCourses) {
        this.programCourses = programCourses;
    }

    
}
