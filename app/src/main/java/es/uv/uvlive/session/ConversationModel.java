package es.uv.uvlive.session;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.gateway.response.ConversationResponse;

public class ConversationModel {

    private long id;
    private String name;

    public ConversationModel(ConversationTable conversationTable) {
        id = conversationTable.getId();
        name = conversationTable.getName();
    }

    public ConversationModel(ConversationResponse conversationResponse) {
        id = conversationResponse.getId();
        name = conversationResponse.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<ConversationModel> transform(List<ConversationTable> conversationTableList) {
        ArrayList<ConversationModel> conversationModelArrayList = new ArrayList<>();

        for (ConversationTable conversationTable: conversationTableList) {
            conversationModelArrayList.add(new ConversationModel(conversationTable));
        }
        return conversationModelArrayList;
    }

    public static List<ConversationModel> transform(ArrayList<ConversationResponse> conversationResponseArrayList) {
        ArrayList<ConversationModel> conversationModelArrayList = new ArrayList<>();

        for (ConversationResponse conversationResponse: conversationResponseArrayList) {
            conversationModelArrayList.add(new ConversationModel(conversationResponse));
        }
        return conversationModelArrayList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj!= null && obj instanceof ConversationModel) {
            return ((ConversationModel) obj).id == id && ((ConversationModel) obj).name.equals(name);
        }
        return false;
    }
}
