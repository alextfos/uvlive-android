package es.uv.uvlive.ui.actions;

import java.util.List;

import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.ui.models.MessageModel;

public interface MessageActions extends BaseActions {
    interface Adapter {
        void onMessagesReceived(List<MessageModel> messageList);
        void onMessageChanged(MessageModel messageModel);
        void addItemListToTop(List<MessageModel> messageModelList);
        void addItemListToBottom(List<MessageModel> messageModelList);
        void addItemToBottom(MessageModel messageModel);
    }
    int getIdConversation();
    void onErrorFetchingMessages(BusinessError errorGettingConversation);
}
