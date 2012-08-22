package tool.email.sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.event.ConnectionListener;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.smtp.SMTPMessage;

/**
 * @author dhf
 */
public final class SendJob implements Runnable {
    private String id = this.hashCode() + "";

    private InternetAddress from = null;

    private Collection<InternetAddress> to = new LinkedHashSet<InternetAddress>();

    private Collection<InternetAddress> cc = new LinkedHashSet<InternetAddress>();

    private Collection<InternetAddress> bcc = new LinkedHashSet<InternetAddress>();

    private Collection<InternetAddress> realRecipients = new LinkedHashSet<InternetAddress>();

    private Collection<Attachment> attachments = new LinkedHashSet<Attachment>();

    private Collection<Header> headers = new LinkedHashSet<Header>();

    private String subject = null;

    private String subjectEncoding = "UTF-8";

    private String content = null;

    private String contentEncoding = "UTF-8";

    private String attachmentEncoding = "UTF-8";

    private boolean success = false;

    private boolean sendPartial = true;

    private boolean htmlContent = true;

    private Collection<TransportListener> transportListeners = new LinkedList<TransportListener>();

    private Collection<ConnectionListener> connectionListeners = new LinkedList<ConnectionListener>();

    private Throwable exception = null;

    private Connection connection = null;

    @Override
    public void run() {
        send();
    }

    public boolean send() {
        synchronized (connection) {
            Transport transport = null;
            try {
                if (!connection.isConnected()) {
                    connection.connect();
                }
                transport = connection.getTransport();

                if (null == this.from) {
                    this.from = new InternetAddress(connection
                            .getConnectionParams().getEnvelopeFrom(),
                            connection.getConnectionParams().getHeloName(),
                            "UTF-8");
                }

                SMTPMessage message = new SMTPMessage(connection.getSession());
                message.setSendPartial(this.sendPartial);

                for (Header h: this.headers) {
                    message.addHeader(h.getName(), h.getValue());
                }

                message.setSubject(this.subject, this.subjectEncoding);

                if (attachments.size() == 0) {
                    message.setText(this.content, this.contentEncoding,
                            htmlContent ? "html" : "plain");
                } else {
                    Multipart multipart = new MimeMultipart();

                    BodyPart mdpContent = new MimeBodyPart();
                    mdpContent.setContent(this.content,
                            (htmlContent ? "text/html;" : "text/plain;")
                                    + "charset=" + this.contentEncoding);
                    multipart.addBodyPart(mdpContent);

                    for (Attachment attachment: attachments) {
                        if (null != attachment.getFile()) {
                            BodyPart mdp = new MimeBodyPart();
                            FileDataSource fds = new FileDataSource(
                                    attachment.getFile());
                            DataHandler dh = new DataHandler(fds);
                            mdp.setDataHandler(dh);
                            String fileName = attachment.getName();
                            if (null == fileName) {
                                fileName = attachment.getFile().getName();
                            }
                            mdp.setFileName(MimeUtility.encodeText(fileName,
                                    attachmentEncoding, "B"));

                            if (attachment.isInline()) {
                                mdp.setDisposition(BodyPart.INLINE);
                                mdp.setHeader("Content-ID",
                                        "<" + attachment.getName() + ">");
                            }
                            multipart.addBodyPart(mdp);
                        }
                    }
                    message.setContent(multipart);
                }

                for (TransportListener l: transportListeners) {
                    transport.addTransportListener(l);
                }
                for (ConnectionListener l: connectionListeners) {
                    transport.addConnectionListener(l);
                }

                message.setEnvelopeFrom(connection.getConnectionParams()
                        .getEnvelopeFrom());
                message.setFrom(this.from);
                message.setSender(this.from);

                message.setRecipients(RecipientType.TO,
                        to.toArray(new Address[] {}));
                message.setRecipients(RecipientType.CC,
                        cc.toArray(new Address[] {}));
                message.setRecipients(RecipientType.BCC,
                        bcc.toArray(new Address[] {}));
                Address[] rcptTos = null;
                if (realRecipients.size() > 0) {
                    rcptTos = realRecipients.toArray(new Address[] {});
                } else {
                    rcptTos = message.getAllRecipients();
                }
                transport.sendMessage(message, rcptTos);
                this.success = true;
                this.exception = null;
                return this.success;
            } catch (Throwable e) {
                this.success = false;
                this.exception = e;
                return this.success;
            } finally {
                for (TransportListener l: transportListeners) {
                    transport.removeTransportListener(l);
                }
                for (ConnectionListener l: connectionListeners) {
                    transport.removeConnectionListener(l);
                }
                if (!connection.getConnectionParams().isKeepAlive()) {
                    connection.close();
                }
            }
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public InternetAddress getMailFrom() {
        return from;
    }

    public void setMailFrom(InternetAddress from) {
        this.from = from;
    }

    public Collection<InternetAddress> getMailTo() {
        return to;
    }

    public void addMailTo(InternetAddress addr) {
        to.add(addr);
    }

    public void clearMailTo() {
        to.clear();
    }

    public Collection<InternetAddress> getMailCc() {
        return cc;
    }

    public void addMailCc(InternetAddress addr) {
        cc.add(addr);
    }

    public void clearMailCc() {
        cc.clear();
    }

    public Collection<InternetAddress> getMailBcc() {
        return bcc;
    }

    public void addMailBcc(InternetAddress addr) {
        bcc.add(addr);
    }

    public void clearMailBcc() {
        bcc.clear();
    }

    public Collection<InternetAddress> getRealRecipients() {
        return realRecipients;
    }

    public void addRealRecipient(InternetAddress addr) {
        realRecipients.add(addr);
    }

    public void clearRealRecipient() {
        realRecipients.clear();
    }

    public Collection<InternetAddress> getAllRecipients() {
        Collection<InternetAddress> result = new LinkedHashSet<InternetAddress>();
        result.addAll(to);
        result.addAll(cc);
        result.addAll(bcc);
        return result;
    }

    public void clearAllRecipients() {
        to.clear();
        cc.clear();
        bcc.clear();
    }

    public void addAttachment(String name, String filePath, boolean inline)
            throws FileNotFoundException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException("Attachment file [" + filePath
                    + "] not found");
        }
        Attachment attachment = new Attachment();
        attachment.setFile(f);
        attachment.setName(name);
        attachment.setInline(inline);
        attachments.add(attachment);
    }

    public void addAttachment(String name, String filePath)
            throws FileNotFoundException {
        addAttachment(name, filePath, false);
    }

    public void addAttachment(String filePath) throws FileNotFoundException {
        addAttachment(null, filePath);
    }

    public void addAttachment(Attachment attachment) {
        if (null != attachment) {
            attachments.add(attachment);
        }
    }

    public void clearAttachments() {
        attachments.clear();
    }

    public String getAttachmentEncoding() {
        return attachmentEncoding;

    }

    public void setAttachmentEncoding(String attachmentEncoding) {
        this.attachmentEncoding = attachmentEncoding;
    }

    public Collection<Header> getHeaders() {
        return headers;
    }

    public void addHeader(String name, String value) {
        headers.add(new Header(name, value));
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectEncoding() {
        return subjectEncoding;
    }

    public void setSubjectEncoding(String subjectEncoding) {
        this.subjectEncoding = subjectEncoding;
    }

    public String getMailContent() {
        return content;
    }

    public void setMailContent(String content) {
        this.content = content;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public boolean isHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(boolean htmlContent) {
        this.htmlContent = htmlContent;
    }

    public boolean isSuccess() {
        return success;
    }

    void setSuccess(boolean success) {
        this.success = success;
    }

    public Throwable getException() {
        return exception;
    }

    void setException(Throwable exception) {
        this.exception = exception;
    }

    public boolean isSendPartial() {
        return sendPartial;
    }

    public void setSendPartial(boolean sendPartial) {
        this.sendPartial = sendPartial;
    }

    public Collection<TransportListener> getTransportListeners() {
        return transportListeners;
    }

    public void addTransportListener(TransportListener l) {
        if (null != l) {
            transportListeners.add(l);
        }
    }

    public void clearTransportListeners() {
        transportListeners.clear();
    }

    public Collection<ConnectionListener> getConnectionListeners() {
        return connectionListeners;
    }

    public void addConnectionListener(ConnectionListener l) {
        if (null != l) {
            connectionListeners.add(l);
        }
    }

    public void clearConnectionListeners() {
        connectionListeners.clear();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
