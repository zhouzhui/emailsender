package tool.email.sender;

import java.io.Serializable;

/**
 * @author dhf
 */
public class Attachment implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8504839753973747724L;

    private String name;

    private byte[] bytes;

    private boolean inline;

    public String getName() {
        return name;
    }

    public Attachment setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Attachment setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public boolean isInline() {
        return inline;
    }

    public Attachment setInline(boolean inline) {
        this.inline = inline;
        return this;
    }

}
