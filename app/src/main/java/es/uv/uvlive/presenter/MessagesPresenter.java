package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

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
    private MessageActions.Adapter adapter;
    private boolean fetchingPreviousMessages = false;

    public MessagesPresenter(final int idConversation, MessageActions messageActions, MessageActions.Adapter adapter) {
        this.messageActions = messageActions;
        this.adapter = adapter;

        conversation = ((RolUV)getSession().getCurrentUser()).getConversation(idConversation);
        Log.d(MessagesPresenter.class.getSimpleName(),"Conversation is null?: " + Boolean.valueOf(conversation==null).toString());
        if (conversation == null) {
            this.messageActions.onErrorFetchingMessages(BusinessError.ERROR_GETTING_CONVERSATION);
        }
    }

    public void init() {
        getLocalMessages();
    }

    public String getTitle() {
        return conversation.getName();
    }

    public void onTopOfMessageList() {
        if (isValidConversation()) {
            fetchingPreviousMessages = true;
            getPreviousMessages();

        }
    }

    public int getIdConversation() {
        if (isValidConversation()) {
            return conversation.getIdConversation();
        } else {
            return -1;
        }
    }

    public boolean isFetchingPreviousMessages() {
        return fetchingPreviousMessages;
    }

    public boolean isEndOfListLoaded() {
        return conversation.isEndOfListLoaded();
    }

    public void getMessages() {
        if (!isValidConversation()) {
            return;
        }

        conversation.getMessages(new BusinessCallback<List<Message>>() {
            @Override
            public void onDataReceived(@NonNull List<Message> result) {
                adapter.onMessagesReceived(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
            }

            @Override
            public void onError(BusinessError businessError) {
                messageActions.onError(businessError);
            }
        });
    }

    public void getPreviousMessages() {
        if (!isValidConversation()) {
            return;
        }

        conversation.getPreviousMessages(conversation.getOldestMessage(), new BusinessCallback<List<Message>>() {
            @Override
            public void onDataReceived(@NonNull List<Message> result) {
                adapter.addItemListToTop(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
                fetchingPreviousMessages = false;
                if (result.size() == 0) {
                    conversation.setEndOfListLoaded(Boolean.TRUE);
                }
            }

            @Override
            public void onError(BusinessError businessError) {
                fetchingPreviousMessages = false;
                messageActions.onError(businessError);
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
                if (!result.isEmpty()) {
                    adapter.addItemListToBottom(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
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

        conversation.saveLocalMessage(message, new BusinessCallback<Message>() {
            @Override
            public void onDataReceived(@NonNull Message result) {
                adapter.addItemToBottom(MessageMapper.getMessageModelFromMessage(getUser().getOwnerName(),result));
                conversation.sendMessage(result, new BusinessCallback<Message>() {
                    @Override
                    public void onDataReceived(@NonNull Message result) {
                        adapter.onMessageChanged(MessageMapper.getMessageModelFromMessage(getUser().getOwnerName(),result));
                    }

                    @Override
                    public void onError(BusinessError businessError) {
                        // Nothing to do here
                    }
                });
            }

            @Override
            public void onError(BusinessError businessError) {
                // Nothing to do here
            }
        });
    }

    /*
    * Private methods
    * */
    private void getLocalMessages() {
        if (!isValidConversation()) {
            return;
        }

        conversation.getLocalMessages(new BusinessCallback<List<Message>>() {
            @Override
            public void onDataReceived(@NonNull List<Message> result) {
                if (result.size() > 0) {
                    adapter.onMessagesReceived(MessageMapper.getMessageModelListFromMessageList(getUser().getOwnerName(), result));
                    getFollowingMessages();
                } else {
                    getMessages();
                }
            }

            @Override
            public void onError(BusinessError businessError) {
                getMessages();
            }
        });
    }

    private boolean isValidConversation() {
        return conversation != null;
    }
}
