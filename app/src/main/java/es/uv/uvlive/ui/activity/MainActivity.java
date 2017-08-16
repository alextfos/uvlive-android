package es.uv.uvlive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.OnClick;
import es.uv.uvlive.presenter.MainPresenter;
import es.uv.uvlive.ui.actions.MainActions;
import es.uv.uvlive.ui.fragment.AdminOptionsListFragment;
import es.uv.uvlive.ui.fragment.ConversationListFragment;
import es.uv.uvlive.ui.fragment.MerchantFragment;
import es.uv.uvlive.ui.fragment.MessageListFragment;

public class MainActivity extends BaseActivity implements MainActions {
    private boolean mTwoPane = false;
    private MainPresenter mainPresenter;

    @BindView(R.id.fab)
    protected FloatingActionButton fab;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    @Override
    protected void initializePresenter() {
        mainPresenter = new MainPresenter(this);
        mainPresenter.loadSession();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UsersActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadConversations();
        }
    }

    private void loadConversationsFragment() {
        navigateToFragment(ConversationListFragment.newInstance());
        fab.setVisibility(View.VISIBLE);
    }

    private void loadMerchantFragment() {
        navigateToFragment(MerchantFragment.newInstance());
        fab.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mainPresenter.isTeacher()) {
            menu.findItem(R.id.main_menu_block).setVisible(Boolean.TRUE);
            menu.findItem(R.id.main_menu_unblock).setVisible(Boolean.TRUE);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_block:
                startActivityForResult(UsersActivity.getIntent(this,UsersActivity.BLOCK),UsersActivity.REQUEST_CODE);
                return true;
            case R.id.main_menu_unblock:
                startActivityForResult(UsersActivity.getIntent(this,UsersActivity.UNBLOCK),UsersActivity.REQUEST_CODE);
                return true;
            case R.id.main_menu_exit:
                logout();
                mainPresenter.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadAdminMenu() {
        fab.setVisibility(View.GONE);
        navigateToFragment(AdminOptionsListFragment.newInstance());
    }


    public void onItemSelected(int id) {
        fab.setVisibility(View.GONE);
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, MessageListFragment.newInstance(id))
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            /*Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(MessageListFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
            */
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, MessageListFragment.newInstance(id))
                    .addToBackStack(null)
                    .commit();
            //getSupportFragmentManager().executePendingTransactions();

            //TODO: reemplazar el fragmento de la derecha
        }
    }

    private void navigateToFragment(Fragment fragment) {
        if (mTwoPane) {

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout, fragment)
                    .commit();
        }
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        startActivityForResult(UsersActivity.getIntent(this), UsersActivity.REQUEST_CODE);
    }

    @Override
    public void loadConversations() {
        loadConversationsFragment();
    }

    @Override
    public void loadMerchantPanel() {
        loadMerchantFragment();
    }
}
