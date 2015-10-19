package by.auto.service.email;

import java.io.Serializable;

public class AttachmentFile implements Serializable {

    private String attachmentName;

    private String fileName;

    private int size = 0;

    private byte content[];

    public AttachmentFile(final String name, final String fileName) {
        super();
        setName(name);
        setFileName(fileName);
    }

    public AttachmentFile(final String name, final byte[] content) {
        super();
        setName(name);
        setContent(content);
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(final byte[] content) {
        this.content = content;
        this.fileName = null;
        if (content != null) {
            size = content.length;
        } else {
            size = 0;
        }
    }

    public String getName() {
        return attachmentName;
    }

    public void setName(final String name) {
        this.attachmentName = name;
    }

    public int getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
        this.content = null;
        this.size = 0;
    }
}
