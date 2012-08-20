package tool.email.sender;

/**
 * @author dhf
 */
public class ConnectionException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -6739581781017728063L;

    public ConnectionException() {
        super();
    }

    public ConnectionException(String msg) {
        super(msg);
    }

    public ConnectionException(Throwable t) {
        super(t);
    }

    public ConnectionException(String msg, Throwable t) {
        super(msg, t);
    }

}
