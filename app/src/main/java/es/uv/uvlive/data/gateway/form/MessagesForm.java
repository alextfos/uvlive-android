package es.uv.uvlive.data.gateway.form;

public class MessagesForm extends BaseForm {

    private int idConversation;
    private int timestamp;

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
