package com.example.atraverf.uvlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.atraverf.uvlive.R;

/**
 * Created by atraverf on 25/12/15.
 */
public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane=false;
    private static int LOGIN_REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            }
            startActivity(new Intent(MainActivity.this, ItemListActivity.class));
        }
    }
}
