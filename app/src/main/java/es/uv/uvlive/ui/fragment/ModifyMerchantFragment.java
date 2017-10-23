package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atraverf.uvlive.R;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.MerchantRegisterForm;
import es.uv.uvlive.data.gateway.response.MerchantResponse;

public class ModifyMerchantFragment extends BaseFragment {
    private View registerFragment;
    private View noResults;
    private EditText searchBar;
    private ImageView searchButton;

    private EditText registerFragmentDni;
    private EditText registerFragmentUsername;
    private EditText registerFragmentName;
    private EditText registerFragmentLastName;
    private EditText registerFragmentPassword;
    private EditText registerFragmentPassword2;
    private Button registerFragmentButton;

    public static ModifyMerchantFragment newInstance() {
        ModifyMerchantFragment fragment = new ModifyMerchantFragment();
        return fragment;
    }

    public ModifyMerchantFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_merchant;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bindViews(view);
        setData();
        setListeners();
    }

    private void bindViews(View rootView) {
        searchButton = (ImageView) rootView.findViewById(R.id.modify_merchant_searchbutton);
        searchBar = (EditText) rootView.findViewById(R.id.modify_merchant_searchbar);

        noResults = rootView.findViewById(R.id.no_results);
        registerFragment = rootView.findViewById(R.id.merchant_form);
        registerFragmentDni = (EditText) registerFragment.findViewById(R.id.fragment_merchant_register_dni);
        registerFragmentUsername = (EditText) registerFragment.findViewById(R.id.fragment_merchant_register_username);
        registerFragmentName = (EditText) registerFragment.findViewById(R.id.fragment_merchant_register_name);
        registerFragmentLastName = (EditText) registerFragment.findViewById(R.id.fragment_merchant_register_lastname);
        registerFragmentPassword = (EditText) registerFragment.findViewById(R.id.fragment_merchant_register_password_1);
        registerFragmentPassword2 = (EditText) registerFragment.findViewById(R.id.fragment_merchant_register_password_2);
        registerFragmentButton = (Button) registerFragment.findViewById(R.id.fragment_merchant_register_button);
    }

    private void setData() {
        registerFragmentButton.setText("Modificar");
    }

    private void setListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMerchantsByFilter();
            }
        });
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    getMerchantsByFilter();
                    handled = true;
                }
                return handled;
            }
        });

        registerFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO save information
                // TOODO check if exists new username
                Toast.makeText(getContext(), "No implementado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMerchantsByFilter() {
        MerchantRegisterForm result = new MerchantRegisterForm();

        final String username = searchBar.getText().toString();
        if (!username.isEmpty()) {
            MerchantRegisterForm request = new MerchantRegisterForm();
            request.setUserName(username);

            UVCallback<MerchantResponse> uvCallback = new UVCallback<MerchantResponse>() {
                @Override
                public void onSuccess(@NonNull MerchantResponse merchantResponse) {
                    if (validMerchant(merchantResponse)) {
                        noResults.setVisibility(View.GONE);
                        registerFragment.setVisibility(View.VISIBLE);
                        fillFields(merchantResponse);
                    } else {
                        noResults.setVisibility(View.VISIBLE);
                        registerFragment.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(int errorCode) {
                    noResults.setVisibility(View.VISIBLE);
                    registerFragment.setVisibility(View.GONE);
                }
            };

            UVLiveApplication.getUVLiveGateway().getMerchant(request, uvCallback);
        }
    }

    private void fillFields(MerchantResponse merchant) {
        // Not null merchant verified
        registerFragmentDni.setText(merchant.getDni());
        registerFragmentUsername.setText(merchant.getUsername());
        registerFragmentName.setText(merchant.getFirstname());
        registerFragmentLastName.setText(merchant.getLastname());
    }

    private boolean validMerchant(MerchantResponse merchantResponse) {
        return merchantResponse.getDni() != null && !merchantResponse.getDni().isEmpty() &&
                merchantResponse.getDni() != null && !merchantResponse.getDni().isEmpty() &&
                merchantResponse.getUsername() != null && !merchantResponse.getUsername().isEmpty() &&
                merchantResponse.getFirstname() != null && !merchantResponse.getFirstname().isEmpty() &&
                merchantResponse.getLastname() != null && !merchantResponse.getLastname().isEmpty();
    }
}
