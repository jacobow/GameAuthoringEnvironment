package configuration;
import java.io.IOException;
import java.util.Properties;
import gamedata.exceptions.FileIOError;
/**
 * This is the global instance of the language currently being used in the Player GUI. It follows the Singleton
 * Design Pattern, meaning that it is only ever created once; reinstantiating it will reset the PlayerLanguage
 * to the default Language (English)
 * @author Brian
 *
 */
public class MenuLanguage {
    
    public static final String DEFAULT = "English";
    private static String currentString = DEFAULT;
    private final String PATH = "resources/languages/";
    private final String PROPERTIES = ".properties";
    private static MenuLanguage instance = new MenuLanguage(currentString);
    
    private Properties props = new Properties();
    
    private MenuLanguage(String language){
        try {
            System.out.println(PATH + language + PROPERTIES);
            this.props.load(getClass().getClassLoader().getResourceAsStream(PATH + language + PROPERTIES));
        }
        catch (IOException e) {
            IOError();
        }
    }
    
    /**
     * Change Language based on ComboBox with language choices
     */
    public static void setLanguage(String language){
        currentString = language;
    }
    
    private void IOError(){
        FileIOError error = new FileIOError("Not a Valid Language, Try Again!");
        error.displayMessage();
    }
    
    public static synchronized MenuLanguage getInstance(){
        return instance;
    }
    
    public String getValue(String propKey){
        return this.props.getProperty(propKey);
    }
}