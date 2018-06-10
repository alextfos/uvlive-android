package es.uv.uvlive.mappers;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.gateway.response.ConversationResponse;
import es.uv.uvlive.session.Conversation;
import es.uv.uvlive.ui.models.ConversationModel;
import es.uv.uvlive.utils.StringUtils;

public final class ConversationMapper {

    private ConversationMapper() {
        throw new RuntimeException("Class can't be instatiated");
    }

    public static List<Conversation> getBusinessObjectFromDB(String ownerName, List<ConversationTable> conversationTableList) {
        ArrayList<Conversation> conversationArrayList = new ArrayList<>();

        for (ConversationTable conversationTable: conversationTableList) {
            conversationArrayList.add(new Conversation(ownerName, conversationTable));
        }
        return conversationArrayList;
    }

    public static List<Conversation> getBusinessObject(String ownerName, List<ConversationResponse> conversationResponseArrayList) {
        ArrayList<Conversation> conversationArrayList = new ArrayList<>();

        for (ConversationResponse conversationResponse: conversationResponseArrayList) {
            Conversation conversation = new Conversation(ownerName,conversationResponse);
            conversationArrayList.add(conversation);
        }
        return conversationArrayList;
    }

    public static List<ConversationModel> getConversationModelListFromConversationList(@Nullable List<Conversation> conversationList) {
        List<ConversationModel> conversationModelList = new ArrayList<>();

        if (conversationList != null && !conversationList.isEmpty()) {
            for (Conversation conversation: conversationList) {
                conversationModelList.add(getConversationModelFromConversation(conversation));
            }
        }

        return conversationModelList;
    }

    public static ConversationModel getConversationModelFromConversation(@NonNull Conversation conversation) {
        ConversationModel conversationModel = new ConversationModel();

        conversationModel.setId(conversation.getId());
        conversationModel.setName(conversation.getName());
        conversationModel.setGrouped(isGroup(conversation.getParticipant1(), conversation.getParticipant2()));

        return conversationModel;
    }

    public static ConversationTable getConversationTableFromConversation(Conversation conversation) {
        ConversationTable conversationTable = new ConversationTable();

        conversationTable.setId(conversation.getId());
        conversationTable.setName(conversation.getName());
        conversationTable.setParticipant1(conversation.getParticipant1());
        conversationTable.setGetParticipant2(conversation.getParticipant2());

        return conversationTable;
    }

    private static boolean isGroup(String participant1, String participant2) {
        return StringUtils.isBlank(participant1) && StringUtils.isBlank(participant2);
    }
}
