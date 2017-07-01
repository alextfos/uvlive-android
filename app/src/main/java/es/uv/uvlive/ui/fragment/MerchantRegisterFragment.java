package es.uv.uvlive.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.atraverf.uvlive.R;

import java.security.Key;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.MerchantRegisterForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.ValidateMerchantResponse;

/**
 * Created by alextfos on 22/01/2017.
 */

public class MerchantRegisterFragment extends BaseFragment {

    @BindView(R.id.fragment_merchant_container)
    protected ViewGroup container;
    @BindView(R.id.fragment_merchant_register_dni)
    protected TextInputEditText dniEt;
    @BindView(R.id.fragment_merchant_register_username)
    protected TextInputEditText userEt;
    @BindView(R.id.fragment_merchant_register_name)
    protected TextInputEditText nameEt;
    @BindView(R.id.fragment_merchant_register_lastname)
    protected TextInputEditText lastNameEt;
    @BindView(R.id.fragment_merchant_register_password_1)
    protected TextInputEditText password1Et;
    @BindView(R.id.fragment_merchant_register_password_2)
    protected TextInputEditText password2Et;
    @BindView(R.id.fragment_merchant_register_button)
    protected Button registerBtn;

    protected HashMap<String, Boolean> formValidation;

    public static MerchantRegisterFragment newInstance() {
        MerchantRegisterFragment fragment = new MerchantRegisterFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_merchant_register, container, false);

        ButterKnife.bind(this,rootView);

        initForm();
        return rootView;
    }

    private void initForm() {
        formValidation = new HashMap<String, Boolean>();
        formValidation.put("dni", false);
        formValidation.put("userName", false);
        formValidation.put("name", false);
        formValidation.put("lastName", false);
        formValidation.put("password", false);
    }

    @OnFocusChange(R.id.fragment_merchant_register_username)
    public void onUserNameChanged(View v, boolean hasFocus) {
        if (!hasFocus) {
            final String username = userEt.getText().toString();
            if (!username.isEmpty()) {
                MerchantRegisterForm request = new MerchantRegisterForm();
                request.setUserName(username);

                UVCallback<ValidateMerchantResponse> uvCallback = new UVCallback<ValidateMerchantResponse>() {
                    @Override
                    public void onSuccess(@NonNull ValidateMerchantResponse validateMerchantResponse) {
                        if (!validateMerchantResponse.isExistingUsername()) {
                            setValidUsername(true);
                            formValidation.put("userName", true);
                        } else {
                            setValidUsername(false);
                            formValidation.put("userName", false);
                            userEt.setError("escoja otro nombre de usuario");
                        }
                    }

                    @Override
                    public void onError(int errorCode) {
                        String.valueOf(errorCode);
                    }
                };

                UVLiveApplication.getUVLiveGateway().merchantValidate(request, uvCallback);
            }
        }
    }

    private void setValidUsername(boolean valid) {
        if (valid) {
            userEt.setHint("Nombre de usuario disponible");
        } else {
            userEt.setHint("nombre de usuario no disponible");
        }
    }

    @OnClick(R.id.fragment_merchant_register_button)
    public void onButtonClick() {

        String dni = dniEt.getText().toString();
        String userName = userEt.getText().toString();
        String name = nameEt.getText().toString();
        String lastName = lastNameEt.getText().toString();
        String password = password1Et.getText().toString();
        String password2 = password2Et.getText().toString();


        //TODO regex
        formValidation.put("dni", true);
        if (!name.isEmpty()) {
            formValidation.put("name", true);
        }
        if (!lastName.isEmpty()) {
            formValidation.put("lastName", true);
        }
        if (!password.isEmpty() && !password2.isEmpty() && password.equals(password2)) {
            formValidation.put("password", true);
        }

        // TODO if valid
        if (isValid()) {
            MerchantRegisterForm request = new MerchantRegisterForm();
            request.setDni(dni);
            request.setUserName(userName);
            request.setFirstName(name);
            request.setLastName(lastName);
            // TODO security
            request.setPassword(password);

            UVCallback<BaseResponse> uvCallback = new UVCallback<BaseResponse>() {
                @Override
                public void onSuccess(@NonNull BaseResponse baseResponse) {
                    Intent intent = new Intent();
                    intent.putExtra("merchantRegistered", true);
                    getActivity().setIntent(intent);
                    getActivity().onBackPressed();
                }

                @Override
                public void onError(int errorCode) {
                    Toast.makeText(getContext(), "No se ha introducido al usuario", Toast.LENGTH_SHORT).show();
                }
            };

            UVLiveApplication.getUVLiveGateway().merchantRegister(request, uvCallback);
        }
    }

    // Move to superclass
    private boolean isValid() {
        boolean valid = true;
        for (String key : formValidation.keySet()) {
            if (!formValidation.get(key)) {
                valid = false;
            }
        }

        return valid;
    }


}
