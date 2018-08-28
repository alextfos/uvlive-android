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
import es.uv.uvlive.presenter.UsersPresenter;
import es.uv.uvlive.session.UserModel;
import es.uv.uvlive.ui.actions.UsersActions;
import es.uv.uvlive.ui.adapter.UsersAdapter;

public class UsersActivity extends BaseActivity implements UsersActions {

    public static final int REQUEST_CODE = 1024;

    private static final String BLOCK_TYPE_KEY = "BLOCK_TYPE_KEY";

    public static final int INIT = 0;
    public static final int BLOCK = 1;
    public static final int UNBLOCK = 2;

    private UsersPresenter presenter;
    private UsersAdapter adapter;

    private int type;

    @BindView(R.id.activity_init_conversation)
    protected RecyclerView recyclerView;

    public static Intent getIntent(Activity activity) {
        return getIntent(activity,INIT);
    }

    public static Intent getIntent(Activity activity, int blockType) {
        Intent intent = new Intent(activity, UsersActivity.class);
        Bundle extras = new Bundle();

        extras.putInt(BLOCK_TYPE_KEY,blockType);
        intent.putExtras(extras);

        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_init_conversation;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getExtras() != null) {
            type = getIntent().getExtras().getInt(BLOCK_TYPE_KEY);
        }
        String title = "";
        switch (type) {
            case INIT:
                title = getString(R.string.init_conversation);
                break;
            case BLOCK:
                title = getString(R.string.block_student);
                break;
            case UNBLOCK:
                title = getString(R.string.unblock_student);
                break;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new UsersPresenter(this);
        presenter.getUsers();
    }

    @Override
    public void onUsersReceived(List<UserModel> usersList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new UsersAdapter(new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(UserModel userModel) {
                if (type == INIT) {
                    presenter.createConversation(userModel);
                } else if (type == BLOCK){
                    presenter.blockStudent(userModel);
                } else if (type == UNBLOCK) {
                    presenter.unblockStudent(userModel);
                }
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
        finishOperation();
    }

    @Override
    public void onStudentBlocked() {
        finishOperation();
    }

    @Override
    public void onStudentUnblocked() {
        finishOperation();
    }

    private void finishOperation() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
