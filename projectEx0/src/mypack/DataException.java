package mypack;



/**
 * throws Data exeption we we desire to.
 * @author https://stackoverflow.com/questions/3776327/how-to-define-custom-exception-class-in-java-the-easiest-way
 */
public class DataException extends Exception {

	public DataException(String message) {
        super(message);
    }
}


