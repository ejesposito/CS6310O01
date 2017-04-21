package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name="persons")
public class Person extends Model 
{

    public interface creation{}
    
    @Id
    private Long id;
    
    @Required(groups = creation.class)
    private String name;

    @Required(groups = creation.class)
    private String address;
    
    @Required(groups = creation.class)
    private String phone;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "person")
    @JsonManagedReference
    private List<Role> roles;
    
    private static final Finder<Long, Person> finder = new Finder<>(Long.class, Person.class);
    
    public static void create (Person object) throws Exception       
    {
        object.save();
    }
    
    public static void update (Person object) throws Exception
    {
        object.update();
    }
    
    public static void delete (Person object) throws Exception
    {
        object.delete();
    }
    
    public static Person findByPropertie(String key,Object obj) throws Exception
    {
        return finder.where().eq(key,obj).findUnique();
    }
    
    public static List<Person> getList () throws Exception
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
     * Serialize a list of objects with all the class attributes 
     * @param objects
     * @return
     * @throws Exception 
     */
    public static JsonNode jsonListSerialization(List<Person> objects) throws Exception
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
    public static Person jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        Person object;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,Person.class);
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * @return the roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
}
