package com.example.atraverf.uvlive.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.atraverf.uvlive.R;
import com.example.atraverf.uvlive.UVLiveApplication;
import com.example.atraverf.uvlive.gateway.form.LoginForm;
import com.example.atraverf.uvlive.gateway.response.LoginResponse;
import com.example.atraverf.uvlive.utils.NavigationUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by atraverf on 17/11/15.
 */
public class LoginActivity extends Activity {

    @InjectView(R.id.login_b)
    Button mLoginButton;

    @InjectView(R.id.login_user_et)
    EditText mUser;

    @InjectView(R.id.login_password_et)
    EditText mPassword;

    @InjectView(R.id.login_spinner)
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.type_login_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        /*mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });*/
    }

    /*
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            return inflater.inflate(R.layout.login,container,false);
        }
        */
    @OnClick(R.id.login_b)
    public void login(){
        LoginForm request = new LoginForm();
        request.setTypeLogin((String)mSpinner.getSelectedItem());
        request.setUser(mUser.getText().toString());
        request.setPassword(mPassword.getText().toString());

        Response.Listener<LoginResponse> responseListener = new Response.Listener<LoginResponse>(){
            @Override
            public void onResponse(LoginResponse loginResponse) {
                Log.d("proves", "vuelta del servidor");
                //startActivity(new Intent(LoginActivity.this,ItemListActivity.class));
                sendResult();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves","Error de respuesta en el login");
            }
        };
        UVLiveApplication.getUVLiveGateway().login(request, responseListener, errorListener);
    }

    private void sendResult(){
        Intent intent = this.getIntent();
        //intent.putExtra("SOMETHING", "EXTRAS");
        this.setResult(Activity.RESULT_OK, intent);
        finish();
    }
}