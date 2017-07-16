package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.atraverf.uvlive.R;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.MerchantRegisterForm;

public class ModifyMerchantFragment extends BaseFragment {
    private View registerFragment;
    private EditText searchBar;
    private ImageButton searchButton;

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
        searchBar = (EditText) rootView.findViewById(R.id.modify_merchant_searchbar);
        searchButton = (ImageButton) rootView.findViewById(R.id.modify_merchant_search);

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
            public void onClick(View v) {
                getMerchantsByFilter();
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

            UVCallback<MerchantRegisterForm> uvCallback = new UVCallback<MerchantRegisterForm>() {
                @Override
                public void onSuccess(@NonNull MerchantRegisterForm merchantRegisterForm) {
                    if (validMerchant(merchantRegisterForm)) {
                        fillFields(merchantRegisterForm);
                    }
                }

                @Override
                public void onError(int errorCode) {
                    // TODO set error to inputLayout
                }
            };

            UVLiveApplication.getUVLiveGateway().getMerchant(request, uvCallback);
        }
    }

    private void fillFields(MerchantRegisterForm merchant) {
        // Not null merchant verified
        registerFragmentDni.setText(merchant.getDni());
        registerFragmentUsername.setText(merchant.getUserName());
        registerFragmentName.setText(merchant.getFirstName());
        registerFragmentLastName.setText(merchant.getLastName());
    }

    private boolean validMerchant(MerchantRegisterForm merchantRegisterForm) {
        return merchantRegisterForm.getDni() != null && !merchantRegisterForm.getDni().isEmpty() &&
        merchantRegisterForm.getDni() != null && !merchantRegisterForm.getDni().isEmpty() &&
        merchantRegisterForm.getUserName() != null && !merchantRegisterForm.getUserName().isEmpty() &&
        merchantRegisterForm.getPassword() != null && !merchantRegisterForm.getPassword().isEmpty() &&
        merchantRegisterForm.getFirstName() != null && !merchantRegisterForm.getFirstName().isEmpty() &&
        merchantRegisterForm.getLastName() != null && !merchantRegisterForm.getLastName().isEmpty();
    }
}
