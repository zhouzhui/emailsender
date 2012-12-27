package tool.email.sender;

import static org.junit.Assert.assertTrue;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dhf
 */
public class SendJobTest {
    private String username = "";

    private String password = "";

    private String smtpHost = "";

    private int smtpPort = 25;

    private String heloName = "";

    private String mailto = "";

    @Before
    public void init() {
        this.username = "fakeuser@example.com";
        this.password = "fakepassword";
        this.smtpHost = "fakesmtp.example.com";
        this.smtpPort = 25;
        this.heloName = "faker";
        this.mailto = "fakereceiver@excample.com";
    }

    @Test
    public void testSend() throws AddressException {
        SendJob job = new SendJob();

        Connection conn = new Connection();
        ConnectionParams params = new ConnectionParams();
        params.setConnectTimeout(1).setDebug(true).setDebugOut(System.out)
                .setEnvelopeFrom(username).setHeloName(heloName)
                .setHost(smtpHost).setKeepAlive(false).setNeedAuth(true)
                .setPassword(password).setPort(smtpPort).setProtocol("smtp")
                .setSocketTimeout(1);
        conn.setConnectionParams(params);
        job.setConnection(conn);
        job.setContentEncoding("utf-8");
        job.setHtmlContent(false);
        job.setMailContent("<script>alert(\"content\");</script>");
        job.setMailFrom(new InternetAddress("admin@heroyang.com"));
        job.setSubject("<script>alert(\"subject\");</script>");
        job.addMailTo(new InternetAddress(mailto));
        boolean success = job.send();
        assertTrue(!success);
    }

}
