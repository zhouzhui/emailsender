package tool.email.sender;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * @author dhf
 */
public class ConnectionPoolFactory implements PoolableObjectFactory<Connection> {
    private ConnectionParams connectionParams;

    public ConnectionPoolFactory(ConnectionParams params) {
        this.connectionParams = params;
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
        Connection conn = new Connection();
        conn.setConnectionParams(connectionParams);
        conn.connect();
        return conn;
    }

    @Override
    public boolean validateObject(Connection conn) {
        return true;
    }

}
