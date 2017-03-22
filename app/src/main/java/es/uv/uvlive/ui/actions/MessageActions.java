package es.uv.uvlive.ui.actions;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.session.MessageModel;

public interface MessageActions {
    public void onMessagesReceived(List<MessageModel> messageModelList);
}
