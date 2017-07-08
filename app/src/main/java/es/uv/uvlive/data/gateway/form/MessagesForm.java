package es.uv.uvlive.data.gateway.form;

import es.uv.uvlive.data.gateway.response.BaseForm;

public class MessagesForm extends BaseForm {

    private int idConversation;
    private int idMessage;

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }
}
