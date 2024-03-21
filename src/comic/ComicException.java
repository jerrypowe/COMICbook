

package comic;

/**
 *
 * @author anhkc
 */
public class ComicException extends Exception {

    public ComicException(String message) {
        super(message);
    }
    
    

    @Override
    public String getMessage() {
        return "ComicException: " + super.getMessage(); 
    }
    
    
}
