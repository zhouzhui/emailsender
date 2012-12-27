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

    public ConnectionParams setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getHost() {
        return host;
    }

    public ConnectionParams setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ConnectionParams setPort(int port) {
        this.port = port;
        return this;
    }

    public String getEnvelopeFrom() {
        return envelopeFrom;
    }

    public ConnectionParams setEnvelopeFrom(String envelopeFrom) {
        this.envelopeFrom = envelopeFrom;
        return this;
    }

    public String getHeloName() {
        return heloName;
    }

    public ConnectionParams setHeloName(String heloName) {
        this.heloName = heloName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ConnectionParams setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public ConnectionParams setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public ConnectionParams setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public ConnectionParams setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public long getSocketTimeout() {
        return socketTimeout;
    }

    public ConnectionParams setSocketTimeout(long socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public PrintStream getDebugOut() {
        return debugOut;
    }

    public ConnectionParams setDebugOut(PrintStream debugOut) {
        this.debugOut = debugOut;
        return this;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public ConnectionParams setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

}
