import java.util.logging.Level;
import models.Program;
import play.*;
import static play.mvc.Results.internalServerError;

public class Global extends GlobalSettings 
{
    private static final Logger.ALogger appLogger = Logger.of("application");
    
    /**
     * @fn onStart(Application app) 
     * @brief App startup
     * @param app 
     */
    @Override 
    public void onStart(Application app) 
    {        
        // Serch for the uinique program
        models.Program program = null;
        try {
            program = Program.findByPropertie("id", 1);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Global.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Create the program for the 
        if (program == null) {
            program = new models.Program();
            program.setName("GTI - Online Program Test");
            program.setCurrentTerm(Program.Term.SUMMER);
            program.setCurrentYear(2017L);    
        }
        try {
            models.Program.create(program);
        } catch (Exception e) {
            appLogger.error("Error in program init", e);
        }
    }
}
