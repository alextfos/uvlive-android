package es.uv.uvlive.data.gateway.form;

public class MessageForm extends BaseForm {
    private int idConversation;
    private String message;

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
