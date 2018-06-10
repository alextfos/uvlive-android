package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.ui.actions.BaseActions;

public abstract class BaseFragment extends Fragment implements BaseActions {

    @Override
    public void onError(BusinessError businessError) {
        if (getActivity() != null) {
            ((BaseActions) getActivity()).onError(businessError);
        }
    }

    /*
    * Template to inject views
    * */
    protected @LayoutRes int getLayoutId() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }
}
