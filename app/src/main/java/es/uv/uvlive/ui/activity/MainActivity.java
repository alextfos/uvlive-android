package es.uv.uvlive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.atraverf.uvlive.R;

import es.uv.uvlive.presenter.MainPresenter;
import es.uv.uvlive.ui.actions.MainActions;
import es.uv.uvlive.ui.fragment.AdminOptionsListFragment;
import es.uv.uvlive.ui.fragment.ConversationListFragment;
import es.uv.uvlive.ui.fragment.MessageListFragment;

public class MainActivity extends BaseActivity implements MainActions {

    private static int LOGIN_REQUEST_CODE = 100;

    private boolean mTwoPane=false;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane=true;
        }

        //TODo replace to right method call
//        if (UVLiveApplication.getInstance().getToken() != null) {
//            initializePresenter();
//        } else {
//            startLoginActivity();
//        }
    }

    private void startLoginActivity() {
        startActivityForResult(new Intent(MainActivity.this,LoginActivity.class),LOGIN_REQUEST_CODE);
    }

    @Override
    protected void initializePresenter() {
        mainPresenter = new MainPresenter(this);
        mainPresenter.loadSession();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_REQUEST_CODE) {
            initializePresenter();
        }
    }

    private void loadConversationsFragment() {
        //TODO: si es pantalla de tablet, meter los dos fragments
        // y si no meter sólo uno
        if (mTwoPane) {

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout, ConversationListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_exit:
                startLoginActivity();
                mainPresenter.logout();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadAdminMenu() {
        if (mTwoPane) {

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, AdminOptionsListFragment.newInstance())
                    .commit();
//            getSupportFragmentManager().executePendingTransactions();
        }
    }


    public void onItemSelected(int id) {
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

    @Override
    public void loadConversations() {
        loadConversationsFragment();
    }
}
