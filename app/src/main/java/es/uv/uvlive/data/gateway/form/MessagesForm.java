package es.uv.uvlive.data.gateway.form;

public class MessagesForm extends BaseForm {

    private int idConversation;
    private long timestamp;

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
