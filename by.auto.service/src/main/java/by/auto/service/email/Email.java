package by.auto.service.email;

import by.auto.domain.common.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Email implements Serializable {

    private Account receiver;

    private String receiverString;

    private EmailType type;

    private String sender;

    private Collection<String> ccAddresses;

    private List<AttachmentFile> attachmentFiles;

    /**
     * make it true if use check to receive email or not
     */
    private boolean unsolicited = true;

    private String generatedSubject;

    private String generatedContent;

    public Collection<String> getCcAddresses() {
        return ccAddresses;
    }

    public Account getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setCcAddresses(final Collection<String> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public void setReceiver(final Account receiver) {
        this.receiver = receiver;
    }

    public String getReceiverString() {
        return receiverString;
    }

    public void setReceiverString(String receiverString) {
        this.receiverString = receiverString;
    }

    public void setSender(final String sender) {
        this.sender = sender;
    }

    public void addCcAddress(final String ccAddress) {
        if (ccAddress != null) {
            if (ccAddresses == null) {
                ccAddresses = new ArrayList();
            }
            ccAddresses.add(ccAddress);
        }
    }

    public List<AttachmentFile> getAttachmentFiles() {
        return attachmentFiles;
    }

    public void addAttachmentFile(final AttachmentFile file) {
        if (file != null) {
            if (attachmentFiles == null) {
                attachmentFiles = new ArrayList();
            }
            attachmentFiles.add(file);
        }
    }

    public boolean isUnsolicited() {
        return unsolicited;
    }

    public void setUnsolicited(final boolean unsolicited) {
        this.unsolicited = unsolicited;
    }

    public String getGeneratedSubject() {
        return generatedSubject;
    }

    public void setGeneratedSubject(final String generatedSubject) {
        this.generatedSubject = generatedSubject;
    }

    public String getGeneratedContent() {
        return generatedContent;
    }

    public void setGeneratedContent(final String generatedContent) {
        this.generatedContent = generatedContent;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }
}
