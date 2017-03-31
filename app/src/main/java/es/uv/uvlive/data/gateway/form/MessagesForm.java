package es.uv.uvlive.data.gateway.form;

import es.uv.uvlive.data.gateway.response.BaseForm;

public class MessagesForm extends BaseForm {

    private long idConversation;
    private long lastMessage;

    public void setIdConversation(long idConversation) {
        this.idConversation = idConversation;
    }

    public void setLastMessage(long lastMessage){
        this.lastMessage = lastMessage;
    }
}
