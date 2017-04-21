package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import play.db.ebean.Model;

@Entity
@Inheritance
@DiscriminatorColumn
@Table(name="roles")
public class Role extends Model 
{

    public interface creation{}
    
    @Id
    private Long id;
    
    private String type;
    
    @ManyToOne
    @JoinColumn(name="person_id")
    @JsonBackReference
    private Person person;
    
    private static final Finder<Long, Role> finder = new Finder<>(Long.class, Role.class);
    
    public Role () {
        
    }
    
    public Role (Long id, String type) {
        this.id = id;
        this.type = type;
    }
    
    public Role (Long id, String type, Person person) {
        this.id = id;
        this.type = type;
        this.person = person;
    }
    
    public static void create (Role object) throws Exception       
    {
        object.save();
    }
    
    public static void update (Role object) throws Exception
    {
        object.update();
    }
    
    public static void delete (Role object) throws Exception
    {
        object.delete();
    }
    
    public static Role findByPropertie(String key,Object obj) throws Exception
    {
        return finder.where().eq(key,obj).findUnique();
    }
    
    public static List<Role> getList () throws Exception
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
        mapper.enableDefaultTyping();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat);       
        jsonError = mapper.convertValue(this, JsonNode.class);
        return jsonError;
    }
        
   /**
     * Serialize a list of objects with all the class attributes 
     * @param objects
     * @return
     * @throws Exception 
     */
    public static JsonNode jsonListSerialization(List<Role> objects) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
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
    public static Role jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        Role object;
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,Role.class);
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
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
}
