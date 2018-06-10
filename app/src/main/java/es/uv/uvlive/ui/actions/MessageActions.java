package es.uv.uvlive.ui.actions;

import java.util.List;

import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.ui.models.MessageModel;

public interface MessageActions extends BaseActions {
    int getIdConversation();
    void onMessagesReceived(List<MessageModel> messageList);
    void getMessages();
    void onErrorFetchingMessages(BusinessError errorGettingConversation);
}
