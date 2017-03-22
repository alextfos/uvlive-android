package es.uv.uvlive.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.gateway.form.ConversationsForm;
import es.uv.uvlive.data.gateway.response.ConversationResponse;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.session.ConversationModel;
import es.uv.uvlive.ui.actions.ConversationsActions;

/**
 * Created by alextfos on 07/12/2016.
 */

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

        // Add some sample items.
        ConversationsForm request = new ConversationsForm();
        request.setId("46821342");

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
        UVLiveApplication.getUVLiveGateway().conversations(request, responseListener, errorListener);
    }
}
