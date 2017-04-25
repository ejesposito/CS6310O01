package models;

import com.avaje.ebean.annotation.EnumValue;
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
import javax.persistence.ManyToOne;
import play.db.ebean.Model;

@Entity
@Table(name="records")
public class Record extends Model 
{

    public interface creation{}
    
    public enum Grade {
        @EnumValue("A")
        A("A"),
        @EnumValue("B")
        B("B"),
        @EnumValue("C")
        C("C"),
        @EnumValue("D")
        D("D"),
        @EnumValue("F")
        F("F"),
        @EnumValue("I")
        I("I");
        public String humanFriendlyName;
        private Grade(String humanReadableName) {
            this.humanFriendlyName = humanReadableName;
        }
        @Override
        public String toString() { return humanFriendlyName; }
    }
    
    private Grade grade;
    
    private String comment;
    
    @ManyToOne
    @JoinColumn(name="student_id")
    @JsonBackReference
    private Student student;
    
    @ManyToOne
    private CourseSession courseSession;
    
    @Id
    private Long id;
    
    private static Finder<Long, Record> finder = new Finder<>(Long.class, Record.class);
    
    public static void create (Record object) throws Exception       
    {
        object.save();
    }
    
    public static void update (Record object) throws Exception
    {
        object.update();
    }
    
    public static void delete (Record object) throws Exception
    {
        object.delete();
    }
    
    public static Record findByPropertie(String key,Object obj) throws Exception
    {
        return getFinder().where().eq(key,obj).findUnique();
    }
    
    public static List<Record> getList () throws Exception
    {
        return getFinder().all();
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
    public static JsonNode jsonListSerialization(List<Record> objects) throws Exception
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
    public static Record jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        Record object;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,Record.class);
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
     * @return the finder
     */
    public static Finder<Long, Record> getFinder() {
        return finder;
    }

    /**
     * @param aFinder the finder to set
     */
    public static void setFinder(Finder<Long, Record> aFinder) {
        finder = aFinder;
    }

    /**
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the courseSession
     */
    public CourseSession getCourseSession() {
        return courseSession;
    }

    /**
     * @param courseSession the courseSession to set
     */
    public void setCourseSession(CourseSession courseSession) {
        this.courseSession = courseSession;
    }
    
}
