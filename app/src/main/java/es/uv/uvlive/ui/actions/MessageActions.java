package es.uv.uvlive.ui.actions;

import java.util.List;

import es.uv.uvlive.session.MessageModel;

public interface MessageActions extends BaseActions {
    int getIdConversation();
    void onMessagesReceived(List<MessageModel> messageModelList);
    void getMessages();
}
