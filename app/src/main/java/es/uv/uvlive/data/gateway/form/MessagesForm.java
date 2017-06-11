package es.uv.uvlive.data.gateway.form;

import es.uv.uvlive.data.gateway.response.BaseForm;

public class MessagesForm extends BaseForm {

    private int idConversation;

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public int getIdConversation() {
        return idConversation;
    }
}
