package com.example.atraverf.uvlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.atraverf.uvlive.R;

/**
 * Created by atraverf on 25/12/15.
 */
public class MainActivity extends AppCompatActivity
    implements ItemListFragment.Callbacks
{

    private boolean mTwoPane=false;
    private static int LOGIN_REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });





        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane=true;
        }
        startActivityForResult(new Intent(MainActivity.this,LoginActivity.class),LOGIN_REQUEST_CODE);
            /*if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.root_layout, RageComicListFragment.newInstance(), "rageComicList")
                        .commit();
            }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_REQUEST_CODE) {
            //TODO: si es pantalla de tablet, meter los dos fragments
            // y si no meter sólo uno
            if(mTwoPane){

            }else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameLayout, ItemListFragment.newInstance(), "list")
                        .commit();
            }
            //startActivity(new Intent(MainActivity.this, ItemListActivity.class));
        }
    }


    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            /*Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
            */
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(id)
                    .commit();
            //getSupportFragmentManager().executePendingTransactions();

            //TODO: reemplazar el fragmento de la derecha
        }
    }
}
