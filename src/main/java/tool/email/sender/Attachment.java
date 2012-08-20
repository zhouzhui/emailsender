package tool.email.sender;

import java.io.File;

/**
 * @author dhf
 */
public class Attachment {
    private String name;

    private File file;

    private boolean inline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isInline() {
        return inline;
    }

    public void setInline(boolean inline) {
        this.inline = inline;
    }

}
