package es.uv.uvlive.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.OnClick;
import es.uv.uvlive.presenter.LoginPresenter;
import es.uv.uvlive.presenter.MainPresenter;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Session;
import es.uv.uvlive.ui.actions.MainActions;
import es.uv.uvlive.ui.fragment.AdminOptionsListFragment;
import es.uv.uvlive.ui.fragment.ConversationListFragment;
import es.uv.uvlive.ui.fragment.MerchantFragment;
import es.uv.uvlive.ui.fragment.MessageListFragment;

public class MainActivity extends BaseActivity implements MainActions, NavigationView.OnNavigationItemSelectedListener  {
    private boolean mTwoPane = false;
    private MainPresenter mainPresenter;

    @BindView(R.id.fab)
    protected FloatingActionButton fab;
    private View navbarHeader;
    private Menu drawerMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navbarHeader = navigationView.getHeaderView(0);
        drawerMenu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);

        setupHeader();
        setupDrawerMenu();
    }

    private void setupHeader() {
        LoginPresenter.LoginTypes loginType = mainPresenter.getLoginType();
        TextView rol = (TextView) navbarHeader.findViewById(R.id.header_userrol);
        TextView user = (TextView) navbarHeader.findViewById(R.id.header_username);

        user.setText(mainPresenter.getUsername());

        switch(loginType) {
            case Admin:
                navbarHeader.setBackgroundResource(R.drawable.navbar_bg_admin);
                rol.setText(getResources().getString(LoginPresenter.LoginTypes.Admin.getStringRes()));
                break;
            case Teacher:
                navbarHeader.setBackgroundResource(R.drawable.navbar_bg_teacher);
                rol.setText(getResources().getString(LoginPresenter.LoginTypes.Teacher.getStringRes()));
                break;
            case Student:
                navbarHeader.setBackgroundResource(R.drawable.navbar_bg_student);
                rol.setText(getResources().getString(LoginPresenter.LoginTypes.Student.getStringRes()));
                break;
            case Merchant:
                navbarHeader.setBackgroundResource(R.drawable.navbar_bg_merchant);
                rol.setText(getResources().getString(LoginPresenter.LoginTypes.Merchant.getStringRes()));
                break;
        }
    }

    private void setupDrawerMenu() {
        if (mainPresenter.isTeacher()) {
            drawerMenu.findItem(R.id.main_menu_block).setVisible(Boolean.TRUE);
            drawerMenu.findItem(R.id.main_menu_unblock).setVisible(Boolean.TRUE);
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
            //TODO: reemplazar el fragmento de la derecha
            */
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, MessageListFragment.newInstance(id))
                    .addToBackStack(null)
                    .commit();
            //getSupportFragmentManager().executePendingTransactions();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fab.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.main_menu_block:
                startActivityForResult(UsersActivity.getIntent(this,UsersActivity.BLOCK),UsersActivity.REQUEST_CODE);
            break;
            case R.id.main_menu_unblock:
                startActivityForResult(UsersActivity.getIntent(this,UsersActivity.UNBLOCK),UsersActivity.REQUEST_CODE);
            break;
            case R.id.main_menu_exit:
                logout();
                mainPresenter.logout();
            break;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
