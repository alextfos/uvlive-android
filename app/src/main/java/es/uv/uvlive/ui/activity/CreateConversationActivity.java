package es.uv.uvlive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.atraverf.uvlive.R;

import java.util.List;

import butterknife.BindView;
import es.uv.uvlive.presenter.CreateConversationPresenter;
import es.uv.uvlive.session.UserModel;
import es.uv.uvlive.ui.actions.CreateConversationActions;
import es.uv.uvlive.ui.adapter.UsersAdapter;

public class CreateConversationActivity extends BaseActivity implements CreateConversationActions {

    public static final int REQUEST_CODE = 1024;

    private CreateConversationPresenter presenter;
    private UsersAdapter adapter;

    @BindView(R.id.activity_init_conversation)
    protected RecyclerView recyclerView;

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
        presenter = new CreateConversationPresenter(this);
        presenter.getUsers();
    }

    @Override
    public void onUsersReceived(List<UserModel> usersList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new UsersAdapter(new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(UserModel userModel) {
                presenter.createConversation(userModel);
            }
        },usersList));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
    }

    @Override
    public void onConversationCreated() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
