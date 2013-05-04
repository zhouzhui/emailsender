emailsender
===========

A client for sending email with simple java api

# Dependency
*    javamail 1.4.5+, required
*    commons-pool 1.6, optional
*    junit 4.10, optional
    
# Example

    String subject = "emailsender v1.1 changelog";
    // default: envelopeFrom
    InternetAddress mailfrom = new InternetAddress(
            "fakemailfrom@example.com", "Fake MailFrom");
    InternetAddress mailto = new InternetAddress(
            "fakerecipient@example.com");
    String content = "1. Fluent API for SendJob & Attachment; \n"
            + "2. No default constructor for Connection & SendJob now";
    boolean htmlContent = false;

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
    // if connection need keep alive after sent, you can set:
    // connectionParams.setKeepAlive(true);
    
    // if you need a connection pool, there present a class: ConnectionFactory,
    // which implements org.apache.commons.pool.PoolableObjectFactory
    
    SendJob job = new SendJob(new Connection(connectionParams));
    job.setSubject(subject).setMailFrom(mailfrom).addMailTo(mailto)
            .setMailContent(content).setHtmlContent(htmlContent);

    boolean success = job.send();