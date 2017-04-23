package models;

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
import javax.persistence.OneToMany;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name="courses")
public class Course extends Model 
{

    public interface creation{}
    
    @Id
    private Long id;
    
    @Constraints.Required(groups = creation.class)
    private String title;
    
    private String description;
    
    private Boolean inFall;
    
    private Boolean inSpring;
    
    private Boolean inSummer;

    @ManyToMany
    @JoinTable(name = "course_prerequisites", joinColumns = {
        @JoinColumn(name = "course_id")}, inverseJoinColumns = {
        @JoinColumn(name = "prerequisite_id")})
    private List<Course> prerequisites;
    
    @ManyToMany(mappedBy = "prerequisites")
    public List<Course> inversePrerequisites;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "course")
    private List<CourseSession> sessions;
    
    private static final Finder<Long, Course> finder = new Finder<>(Long.class, Course.class);
    
    public static void create (Course object) throws Exception       
    {
        object.save();
    }
    
    public static void update (Course object) throws Exception
    {
        object.update();
    }
    
    public static void delete (Course object) throws Exception
    {
        object.delete();
    }
    
    public static Course findByPropertie(String key,Object obj) throws Exception
    {
        return finder.where().eq(key,obj).findUnique();
    }
    
    public static List<Course> getList () throws Exception
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
    public static JsonNode jsonListSerialization(List<Course> objects) throws Exception
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
    public static Course jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        Course object;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,Course.class);
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the inFall
     */
    public Boolean getInFall() {
        return inFall;
    }

    /**
     * @param inFall the inFall to set
     */
    public void setInFall(Boolean inFall) {
        this.inFall = inFall;
    }

    /**
     * @return the inSpring
     */
    public Boolean getInSpring() {
        return inSpring;
    }

    /**
     * @param inSpring the inSpring to set
     */
    public void setInSpring(Boolean inSpring) {
        this.inSpring = inSpring;
    }

    /**
     * @return the inSummer
     */
    public Boolean getInSummer() {
        return inSummer;
    }

    /**
     * @param inSummer the inSummer to set
     */
    public void setInSummer(Boolean inSummer) {
        this.inSummer = inSummer;
    }

    /**
     * @return the prerequisites
     */
    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    /**
     * @param prerequisites the prerequisites to set
     */
    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    /**
     * @return the sessions
     */
    public List<CourseSession> getSessions() {
        return sessions;
    }

    /**
     * @param sessions the sessions to set
     */
    public void setSessions(List<CourseSession> sessions) {
        this.sessions = sessions;
    }
    
}
