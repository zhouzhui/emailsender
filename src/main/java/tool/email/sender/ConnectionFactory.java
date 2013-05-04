package tool.email.sender;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * @author dhf
 */
public class ConnectionFactory implements PoolableObjectFactory<Connection> {
    private ConnectionParams connectionParams;

    public ConnectionFactory(ConnectionParams connectionParams) {
        if (null == connectionParams) {
            throw new IllegalArgumentException("connectionParams is null");
        }
        this.connectionParams = connectionParams;
    }

    @Override
    public void activateObject(Connection conn) throws Exception {}

    @Override
    public void passivateObject(Connection conn) throws Exception {}

    @Override
    public void destroyObject(Connection conn) throws Exception {
        conn.close();
    }

    @Override
    public Connection makeObject() throws Exception {
        Connection conn = new Connection(connectionParams);
        conn.connect();
        return conn;
    }

    @Override
    public boolean validateObject(Connection conn) {
        return conn.isConnected();
    }

}
