package com.example.atraverf.uvlive.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.atraverf.uvlive.UVLiveApplication;
import com.example.atraverf.uvlive.data.gateway.form.ConversationsForm;
import com.example.atraverf.uvlive.data.gateway.response.ConversationsListResponse;
import com.example.atraverf.uvlive.ui.actions.ConversationsActions;

/**
 * Created by alextfos on 07/12/2016.
 */

public class ConversationsPresenter extends BasePresenter {

    private ConversationsActions conversationsActions;

    public ConversationsPresenter(ConversationsActions conversationsActions) {
        this.conversationsActions = conversationsActions;
    }


    public void getConversations() {
        //TODO: Buscar en el almacenamiento las conversaciones disponibles, si no hay hacer consulta al servicio
        // Add some sample items.
        ConversationsForm request = new ConversationsForm();
        request.setId("46821342");
        Response.Listener<ConversationsListResponse> responseListener = new Response.Listener<ConversationsListResponse>(){
            @Override
            public void onResponse(ConversationsListResponse conversationsListResponse) {
                conversationsActions.onConversationsReceived(conversationsListResponse);
            }

        };
        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves", "Conversaciones - Error");
            }
        };
        UVLiveApplication.getUVLiveGateway().conversations(request, responseListener, errorListener);

    }
}
