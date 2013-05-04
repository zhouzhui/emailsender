package tool.email.sender;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Test;

/**
 * @author dhf
 */
public class SendJobTest {
    @Test
    public void testSend() throws AddressException,
            UnsupportedEncodingException {
        String username = "fake@example.com";
        String password = "fakepwd";
        String protocol = "smtps"; // default: smtp
        String smtpHost = "smtp.example.com";
        int smtpPort = 465; // default: 25
        long timeoutInMills = 1000L; // default: 5000L

        ConnectionParams connectionParams = new ConnectionParams();
        connectionParams.setConnectTimeout(timeoutInMills)
                .setSocketTimeout(timeoutInMills).setDebug(true)
                .setProtocol(protocol).setHost(smtpHost).setPort(smtpPort)
                .setNeedAuth(true).setEnvelopeFrom(username)
                .setPassword(password);

        String subject = "emailsender v1.1 changelog";
        // default: envelopeFrom
        InternetAddress mailfrom = new InternetAddress(
                "fakemailfrom@example.com", "Fake MailFrom");
        InternetAddress mailto = new InternetAddress(
                "fakerecipient@example.com");
        String content = "1. Fluent API for SendJob & Attachment; \n"
                + "2. No default constructor for Connection & SendJob now";
        boolean htmlContent = false;

        SendJob job = new SendJob(new Connection(connectionParams));
        job.setSubject(subject).setMailFrom(mailfrom).addMailTo(mailto)
                .setMailContent(content).setHtmlContent(htmlContent);

        boolean success = job.send();
        assertTrue(!success);
    }
}
