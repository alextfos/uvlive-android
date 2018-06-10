package es.uv.uvlive.session;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
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

    public void loadConversations(BusinessCallback<List<Conversation>> callback) {
        if (conversationList != null) {
            List<ConversationTable> conversationTableList = SQLite.select()
                    .from(ConversationTable.class)
                    .queryList();
            conversationList = ConversationMapper.getBusinessObjectFromDB(getOwnerName(), conversationTableList);
            callback.onDataReceived(conversationList);
        }
    }

    public void updateConversations(final BusinessCallback<List<Conversation>> callback) {
        UVLiveApplication.getUVLiveGateway().conversations(new UVCallback<ConversationsListResponse>() {
            @Override
            public void onSuccess(@NonNull ConversationsListResponse conversationsListResponse) {
                List<Conversation> conversations =
                        ConversationMapper.getBusinessObject(getOwnerName(),conversationsListResponse.getConversations());
                if (conversationList.isEmpty()) {
                    conversationList = conversations;
                } else {
                    for (Conversation conversation : conversations) {
                        if (!conversationList.contains(conversation)) {
                            ConversationMapper.getConversationTableFromConversation(conversation).save();
                            conversationList.add(conversation);
                        }
                    }
                }
                callback.onDataReceived(conversations);
            }

            @Override
            public void onError(int errorCode) {
                if (conversationList != null) {
                    callback.onDataReceived(conversationList);
                } else {
                    // TODO propagar el error
                }
            }
        });
    }


    public @Nullable Conversation getConversation(int idConversation) {
        Conversation foundConversation = null;
        for (Conversation conversation: conversationList) {
            if (conversation.getId() == idConversation) {
                foundConversation = conversation;
                break;
            }
        }

        return foundConversation;
    }
}
