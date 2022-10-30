package prr.exceptions;

public class CommunicationDestinationIsBusyException extends Exception {
    /** Class serial number. */
    private static final long serialVersionUID = 202208091753L;

    /** The given key. */
    private String _key;

    /**
     * @param key
     */
    public CommunicationDestinationIsBusyException(String key) {
        _key = key;
    }

    /**
     * @param key
     * @param cause
     */
    public CommunicationDestinationIsBusyException(String key, Exception cause) {
        super(cause);
        _key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return _key;
    }
}
