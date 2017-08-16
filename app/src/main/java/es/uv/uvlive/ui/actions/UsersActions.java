package es.uv.uvlive.ui.actions;

import java.util.List;

import es.uv.uvlive.session.UserModel;

public interface UsersActions extends BaseActions {
    void onUsersReceived(List<UserModel> usersList);

    void onConversationCreated();

    void onStudentBlocked();

    void onStudentUnblocked();
}
