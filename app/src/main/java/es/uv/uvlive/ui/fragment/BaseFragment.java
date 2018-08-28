package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import es.uv.uvlive.presenter.BasePresenter;
import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.ui.actions.BaseActions;

public abstract class BaseFragment extends Fragment implements BaseActions {

    @Override
    public void onError(BusinessError businessError) {
        if (getActivity() != null) {
            ((BaseActions) getActivity()).onError(businessError);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
            * Template to inject views
            * */
    protected @LayoutRes int getLayoutId() {
        return 0;
    }

    protected abstract void initializePresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment or reuse the existing one
        View rootView = (getView() != null)?
                getView():
                inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializePresenter();

        if (getPresenter() != null) {
            getPresenter().onCreate();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
    }

    protected @Nullable
    BasePresenter getPresenter() {
        return null;
    }
}
