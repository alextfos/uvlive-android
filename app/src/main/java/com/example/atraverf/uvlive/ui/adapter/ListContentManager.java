package com.example.atraverf.uvlive.ui.adapter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.atraverf.uvlive.UVLiveApplication;
import com.example.atraverf.uvlive.data.gateway.form.ConversationsForm;
import com.example.atraverf.uvlive.data.gateway.response.ConversationResponse;
import com.example.atraverf.uvlive.data.gateway.response.ConversationsListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ListContentManager {

    /**
     * An array of sample (dummy) items.
     */
    private static List<ListItem> ITEMS = new ArrayList<ListItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    private static Map<String, ListItem> ITEM_MAP = new HashMap<String, ListItem>();

    private static int mCount = 0;

    public static void setListItems() {
        //TODO: Buscar en el almacenamiento las conversaciones disponibles, si no hay hacer consulta al servicio
        // Add some sample items.
        ConversationsForm request = new ConversationsForm();
        request.setId("46821342");
        Response.Listener<ConversationsListResponse> responseListener = new Response.Listener<ConversationsListResponse>(){
            @Override
            public void onResponse(ConversationsListResponse conversationsListResponse) {
                Log.d("proves", "Conversaciones - Vuelta del servidor");
                //Recorrer tod el array e instanciar los ListItems

                for(ConversationResponse conv: conversationsListResponse.getConversations()) {
                    ListItem item = new ListItem(Integer.toString(mCount++), conv.getId(), conv.getName());
                    addItem(item);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves", "Conversaciones - Error");
            }
        };
        UVLiveApplication.getUVLiveGateway().conversations(request, responseListener, errorListener);

        /*
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }*/
    }

    public static List<ListItem> getListItems(){
        return ITEMS;
    }

    public static Map<String, ListItem> getItemMap(){
        return ITEM_MAP;
    }
    private static void addItem(ListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ListItem {
        private String id;
        private int idConversation;
        private String name;

        public ListItem(String id, int idConversation, String name) {
            this.id = id;
            this.idConversation = idConversation;
            this.name=name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getId() {
            return id;
        }

        public int getIdConversation () {
            return idConversation;
        }
    }
}
