package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import es.uv.uvlive.mappers.MessageMapper;
import es.uv.uvlive.session.BusinessCallback;
import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.session.Conversation;
import es.uv.uvlive.session.Message;
import es.uv.uvlive.session.RolUV;
import es.uv.uvlive.ui.actions.MessageActions;

public class MessagesPresenter extends BasePresenter {

    private MessageActions messageActions;
    private Conversation conversation;

    private boolean endList = false;

    public MessagesPresenter(int idConversation, MessageActions messageActions) {
        this.messageActions = messageActions;

        conversation = ((RolUV)getSession().getCurrentUser()).getConversation(idConversation);
        if (conversation == null) {
            messageActions.onErrorFetchingMessages(BusinessError.ERROR_GETTING_CONVERSATION);
        }
    }

    private boolean isValidConversation() {
        return conversation != null;
    }

    public void getLocalMessages() {
        if (!isValidConversation()) {
            return;
        }

        conversation.getLocalMessages(new BusinessCallback<List<Message>>() {
            @Override
            public void onDataReceived(@NonNull List<Message> result) {
                messageActions.onMessagesReceived(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
            }

            @Override
            public void onError(BusinessError businessError) {
                messageActions.onError(businessError);
            }
        });
    }

    public void getMessages(boolean endList) {
        this.endList = endList;
        getMessages();
    }

    public void getMessages() {
        if (!isValidConversation()) {
            return;
        }
        if (endList) {
            return;
        }

        conversation.getMessages(conversation.getOldestMessage(),new BusinessCallback<List<Message>>() {
            @Override
            public void onDataReceived(@NonNull List<Message> result) {
                messageActions.onMessagesReceived(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
                if (result.size() == 0) {
                    endList = true;
                }
            }

            @Override
            public void onError(BusinessError businessError) {
                messageActions.onError(businessError);
            }
        });
    }



    public void sendMessage(String message) {
        if (!isValidConversation()) {
            return;
        }
        conversation.sendMessage(message, new BusinessCallback<Message>() {
            @Override
            public void onDataReceived(@NonNull Message result) {
                // TODO
            }

            @Override
            public void onError(BusinessError businessError) {
                // TODO
            }
        });
    }

    public void getFollowingMessages() {
        if (!isValidConversation()) {
            return;
        }
        conversation.getFollowingMessages(conversation.getNewestMessage(),
                new BusinessCallback<List<Message>>() {
            @Override
            public void onDataReceived(@NonNull List<Message> result) {
                messageActions.onMessagesReceived(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
            }

            @Override
            public void onError(BusinessError businessError) {
                messageActions.onError(businessError);
            }
        });
    }

    public void getNewMessages() {
        if (!isValidConversation()) {
            return;
        }
        conversation.getNewMessages(new BusinessCallback<List<Message>>() {
            @Override
            public void onDataReceived(@NonNull List<Message> result) {
                messageActions.onMessagesReceived(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
            }

            @Override
            public void onError(BusinessError businessError) {
                messageActions.onError(businessError);
            }
        });
    }
}
