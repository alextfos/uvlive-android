package es.uv.uvlive.data.gateway.form;

public class MessagesForm {

    private long idConversation;
    private long lastMessage;

    public void setIdConversation(long idConversation) {
        this.idConversation = idConversation;
    }

    public void setLastMessage(long lastMessage){
        this.lastMessage = lastMessage;
    }
}
