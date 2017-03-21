package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.atraverf.uvlive.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by alextfos on 22/01/2017.
 */

public class InfoTraderFragment extends BaseFragment {

    @InjectView(R.id.fragment_info_trader_user)
    protected EditText userEt;
    @InjectView(R.id.fragment_info_trader_name)
    protected EditText nameEt;
    @InjectView(R.id.fragment_info_trader_lastname_1)
    protected EditText lastName1Et;
    @InjectView(R.id.fragment_info_trader_lastname_2)
    protected EditText lastName2Et;
    @InjectView(R.id.fragment_info_trader_password_1)
    protected EditText password1Et;
    @InjectView(R.id.fragment_info_trader_password_2)
    protected EditText password2Et;

    public static InfoTraderFragment newInstance() {
        InfoTraderFragment fragment = new InfoTraderFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_trader, container, false);
        ButterKnife.inject(this,rootView);
        return rootView;
    }
}
