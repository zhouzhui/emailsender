package tool.email.sender;

import java.io.File;
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

    private File file;

    private boolean inline;

    public String getName() {
        return name;
    }

    public Attachment setName(String name) {
        this.name = name;
        return this;
    }

    public File getFile() {
        return file;
    }

    public Attachment setFile(File file) {
        this.file = file;
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
