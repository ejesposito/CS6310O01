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
        // Create the program for the university
        models.Program program = new models.Program();
        program.setName("GTI - Online Program Test");
        program.setCurrentTerm(Program.Term.SUMMER);
        program.setCurrentYear(2017L);
        try {
            models.Program.create(program);
        } catch (Exception e) {
            appLogger.error("Error in program init", e);
        }
    }
}
