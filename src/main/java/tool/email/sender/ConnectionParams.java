package tool.email.sender;

import java.io.PrintStream;

/**
 * @author dhf
 */
public class ConnectionParams {
    private String protocol = "smtp";

    private String host = "";

    private int port = 25;

    private String envelopeFrom = "";

    private String heloName = "";

    private String password = null;

    private boolean needAuth = false;

    private boolean debug = false;

    private long connectTimeout = 5000;

    private long socketTimeout = 5000;

    private PrintStream debugOut = System.out;

    private boolean keepAlive = false;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getEnvelopeFrom() {
        return envelopeFrom;
    }

    public void setEnvelopeFrom(String envelopeFrom) {
        this.envelopeFrom = envelopeFrom;
    }

    public String getHeloName() {
        return heloName;
    }

    public void setHeloName(String heloName) {
        this.heloName = heloName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(long socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public PrintStream getDebugOut() {
        return debugOut;
    }

    public void setDebugOut(PrintStream debugOut) {
        this.debugOut = debugOut;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

}
