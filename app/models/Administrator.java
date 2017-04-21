package models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
public class Administrator extends models.Role 
{

    public interface creation{}
    
    private static final Finder<Long, Administrator> finder = new Finder<>(Long.class, Administrator.class);
    
    public Administrator () {
        
    }
    
    public Administrator (Long id) {
        super(id, "ADMINISTRATOR");
    }
    
    public Administrator (Long id, Person person) {
        super(id, "ADMINISTRATOR", person);
    }
    
    public static void create (Administrator object) throws Exception       
    {
        object.save();
    }
    
    public static void update (Administrator object) throws Exception
    {
        object.update();
    }
    
    public static void delete (Administrator object) throws Exception
    {
        object.delete();
    }
    
    public static Administrator findByPropertie(String key,Object obj) throws Exception
    {
        return finder.where().eq(key,obj).findUnique();
    }
    
    public static List<Administrator> getAdministratorsList () throws Exception
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
    public static JsonNode jsonAdministratorsListSerialization(List<Administrator> objects) throws Exception
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
    public static Administrator jsonDesSerialization(JsonNode jsonObject) throws Exception
    {
        Administrator object;
        ObjectMapper mapper = new ObjectMapper();
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.setDateFormat(myDateFormat); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object = mapper.convertValue(jsonObject,Administrator.class);
        return object;
    }
  
    
}
