package es.uv.uvlive.session;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.gateway.response.ConversationResponse;
import es.uv.uvlive.utils.StringUtils;

public class ConversationModel {
    private int id;
    private String name;
    private boolean isGrouped;

    public static List<ConversationModel> transform(List<ConversationTable> conversationTableList) {
        ArrayList<ConversationModel> conversationModelArrayList = new ArrayList<>();

        for (ConversationTable conversationTable: conversationTableList) {
            // TODO get if grouped
            conversationModelArrayList.add(new ConversationModel(conversationTable));
        }
        return conversationModelArrayList;
    }

    public static List<ConversationModel> transform(String ownerName, ArrayList<ConversationResponse> conversationResponseArrayList) {
        ArrayList<ConversationModel> conversationModelArrayList = new ArrayList<>();

        for (ConversationResponse conversationResponse: conversationResponseArrayList) {
            ConversationModel conversationModel = new ConversationModel(ownerName,conversationResponse);
            conversationModel.setGrouped(isGroped(conversationResponse));
            conversationModelArrayList.add(conversationModel);
        }
        return conversationModelArrayList;
    }

    public static boolean isGroped(ConversationResponse conversationResponse) {
        return conversationResponse.getParticipant1() == null &&
                conversationResponse.getParticipant2() == null;
    }

    public ConversationModel(ConversationTable conversationTable) {
        id = conversationTable.getId();
        name = conversationTable.getName();
    }

    public ConversationModel(String ownerName, ConversationResponse conversationResponse) {
        id = conversationResponse.getId();
        name = conversationResponse.getName();

        if (StringUtils.isBlank(name)) {
            if (conversationResponse.getParticipant1() != null && !conversationResponse.getParticipant1().equals(ownerName)) {
                name = conversationResponse.getParticipant1();
            } else {
                name = conversationResponse.getParticipant2();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGrouped() {
        return isGrouped;
    }

    public void setGrouped(boolean grouped) {
        isGrouped = grouped;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ConversationModel && ((ConversationModel) obj).id == id
                && ((ConversationModel) obj).name != null && ((ConversationModel) obj).name.equals(name);
    }
}
