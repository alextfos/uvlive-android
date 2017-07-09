package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.OnClick;
import es.uv.uvlive.presenter.MerchantPresenter;
import es.uv.uvlive.ui.actions.MerchantActions;
import es.uv.uvlive.utils.StringUtils;

public class MerchantFragment extends BaseFragment implements MerchantActions {

    @BindView(R.id.fragment_merchant_title)
    protected TextInputEditText titleEt;

    @BindView(R.id.fragment_merchant_text)
    protected TextInputEditText textEt;

    private MerchantPresenter presenter;

    public static MerchantFragment newInstance() {
        return new MerchantFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setListeners();
        presenter = new MerchantPresenter(this);
    }

    private void setListeners() {
        textEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    registerBroadcast();
                }
                return false;
            }
        });
    }

    @OnClick(R.id.fragment_merchant_send)
    public void registerBroadcast() {
        String title = titleEt.getText().toString();
        String text = titleEt.getText().toString();
        if (!StringUtils.isBlank(title) && !StringUtils.isBlank(text)) {
            presenter.sendBroadcast(title,text);
        } else {
            Toast.makeText(getContext(),getString(R.string.send_broadcast_warning), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onOk() {
        Toast.makeText(getContext(),getString(R.string.send_broadcast_ok), Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
