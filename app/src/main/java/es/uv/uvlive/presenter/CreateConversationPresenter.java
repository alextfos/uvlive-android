package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.InitConversationForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.UserListResponse;
import es.uv.uvlive.session.UserModel;
import es.uv.uvlive.ui.actions.CreateConversationActions;

public class CreateConversationPresenter extends BasePresenter {

    private CreateConversationActions actions;

    public CreateConversationPresenter(CreateConversationActions actions) {
        this.actions = actions;
    }

    public void getUsers() {
        UVLiveApplication.getUVLiveGateway().getUsers(new UVCallback<UserListResponse>() {
            @Override
            public void onSuccess(@NonNull UserListResponse userListResponse) {
                actions.onUsersReceived(UserModel.transform(userListResponse.getUsers()));
            }

            @Override
            public void onError(int errorCode) {
                actions.onError(errorCode);
            }
        });
    }

    public void createConversation(UserModel userModel) {
        InitConversationForm initConversationForm = new InitConversationForm();
        initConversationForm.setIdUser(userModel.getUserId());

        UVLiveApplication.getUVLiveGateway().initConversation(initConversationForm, new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                actions.onConversationCreated();
            }

            @Override
            public void onError(int errorCode) {
                actions.onError(errorCode);
            }
        });
    }
}
