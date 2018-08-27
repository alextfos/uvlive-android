package es.uv.uvlive.session;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.mappers.ConversationMapper;

public abstract class RolUV extends User {

    private List<Conversation> conversationList;

    public RolUV() {
        conversationList = new ArrayList<>();
        setClazz(getClass().getName());
    }

    public void loadConversations(final BusinessCallback<List<Conversation>> callback) {
        if (conversationList != null) {
            SQLite.select()
                    .from(ConversationTable.class)
                    .async().queryListResultCallback(new QueryTransaction.QueryResultListCallback<ConversationTable>() {
                @Override
                public void onListQueryResult(QueryTransaction transaction, @Nullable List<ConversationTable> tResult) {
                    RolUV.this.conversationList = ConversationMapper.getBusinessObjectFromDB(getOwnerName(), tResult);
                    callback.onDataReceived(conversationList);
                }
            }).execute();
        }
    }

    public void updateConversations(final BusinessCallback<List<Conversation>> callback) {
        UVLiveApplication.getUVLiveGateway().conversations(new UVCallback<ConversationsListResponse>() {
            @Override
            public void onSuccess(@NonNull ConversationsListResponse conversationsListResponse) {
                List<Conversation> conversations =
                        ConversationMapper.getBusinessObject(getOwnerName(),conversationsListResponse.getConversations());

                if (!(conversationList.containsAll(conversations) &&
                        conversationList.size() == conversations.size())) {
                    if (conversationList.isEmpty()) {
                        conversationList.addAll(conversations);
                        for (Conversation conversation: conversationList) {
                            ConversationMapper.getConversationTableFromConversation(conversation).async().save();
                        }
                    } else {
                        for (Conversation conversation : conversations) {
                            if (!conversationList.contains(conversation)) {
                                ConversationMapper.getConversationTableFromConversation(conversation).async().save();
                                conversationList.add(conversation);
                            }
                        }
                    }
                    callback.onDataReceived(conversationList);
                }
            }

            @Override
            public void onError(int errorCode) {
                if (conversationList != null && conversationList.size() == 0) {
                    // TODO propagar el error y mostrar feedback
                }
            }
        });
    }


    public @Nullable Conversation getConversation(int idConversation) {
        Conversation foundConversation = null;
        for (Conversation conversation: conversationList) {
            if (conversation.getIdConversation() == idConversation) {
                foundConversation = conversation;
                break;
            }
        }

        return foundConversation;
    }
}
