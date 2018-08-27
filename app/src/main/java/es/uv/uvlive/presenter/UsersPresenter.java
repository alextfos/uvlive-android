package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.InitConversationForm;
import es.uv.uvlive.data.gateway.form.StudentForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.UserListResponse;
import es.uv.uvlive.mappers.ErrorMapper;
import es.uv.uvlive.session.UserModel;
import es.uv.uvlive.ui.actions.UsersActions;

public class UsersPresenter extends BasePresenter {

    private UsersActions actions;

    public UsersPresenter(UsersActions actions) {
        super();
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
                actions.onError(ErrorMapper.mapError(errorCode));
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
                actions.onError(ErrorMapper.mapError(errorCode));
            }
        });
    }

    public void blockStudent(UserModel userModel) {
        StudentForm studentForm = new StudentForm();
        studentForm.setIdStudent(userModel.getUserId());
        UVLiveApplication.getUVLiveGateway().blockStudent(studentForm, new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                actions.onStudentBlocked();
            }

            @Override
            public void onError(int errorCode) {
                actions.onError(ErrorMapper.mapError(errorCode));
            }
        });
    }

    public void unblockStudent(UserModel userModel) {
        StudentForm studentForm = new StudentForm();
        studentForm.setIdStudent(userModel.getUserId());
        UVLiveApplication.getUVLiveGateway().unblockStudent(studentForm, new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                actions.onStudentUnblocked();
            }

            @Override
            public void onError(int errorCode) {
                actions.onError(ErrorMapper.mapError(errorCode));
            }
        });
    }
}
