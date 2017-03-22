package es.uv.uvlive.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.session.ConversationModel;
import es.uv.uvlive.session.MessageModel;
import es.uv.uvlive.ui.actions.ConversationsActions;

public class ConversationsPresenter extends BasePresenter {

    private ConversationsActions conversationsActions;

    public ConversationsPresenter(ConversationsActions conversationsActions) {
        this.conversationsActions = conversationsActions;
    }

    public void getConversations() {
        List<ConversationTable> conversationTableList = SQLite.select()
                .from(ConversationTable.class)
                .queryList();
        final List<ConversationModel> conversationsDBList = ConversationModel.transform(conversationTableList);
        conversationsActions.onConversationsReceived(conversationsDBList);

        Response.Listener<ConversationsListResponse> responseListener = new Response.Listener<ConversationsListResponse>() {
            @Override
            public void onResponse(ConversationsListResponse conversationsListResponse) {
                List<ConversationModel> conversations =
                        ConversationModel.transform(conversationsListResponse.getConversations());
                for (ConversationModel conversation: conversations) {
                    if (!conversationsDBList.contains(conversation)) {
                        ConversationTable conversationTable = new ConversationTable();
                        conversationTable.setId(conversation.getId());
                        conversationTable.setName(conversation.getName());
                        conversationTable.save();
                    }
                }
                conversationsActions.onConversationsReceived(conversations);
            }

        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves", "Conversaciones - Error");
            }
        };
        UVLiveApplication.getUVLiveGateway().conversations(responseListener, errorListener);
    }
}
