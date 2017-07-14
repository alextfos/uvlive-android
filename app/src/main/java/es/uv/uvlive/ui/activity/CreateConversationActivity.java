package es.uv.uvlive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.atraverf.uvlive.R;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.response.UserListResponse;

public class CreateConversationActivity extends BaseActivity {

    public static Intent getIntent(Activity activity) {
        return new Intent(activity,CreateConversationActivity.class);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_init_conversation;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializePresenter() {
        UVLiveApplication.getUVLiveGateway().getUsers(new UVCallback<UserListResponse>() {
            @Override
            public void onSuccess(@NonNull UserListResponse userListResponse) {

            }

            @Override
            public void onError(int errorCode) {

            }
        });

    }
}
