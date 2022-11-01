package prr.exceptions;

/**
 * Communication key does not exist
 */
public class InvalidCommunicationException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202208091753L;

    /** The unknown key. */
    private int _key;

    /**
     * @param key
     */
    public InvalidCommunicationException(int key) {
        _key = key;
    }

    /**
     * @param key
     * @param cause
     */
    public InvalidCommunicationException(int key, Exception cause) {
        super(cause);
        _key = key;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return _key;
    }

}