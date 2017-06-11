package es.uv.uvlive.ui.fragment;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import es.uv.uvlive.data.ErrorManager;
import es.uv.uvlive.ui.actions.BaseActions;
import es.uv.uvlive.ui.activity.BaseActivity;

public class BaseFragment extends Fragment implements BaseActions {

    @Override
    public void onError(int errorCode) {
        ((BaseActions)getActivity()).onError(errorCode);
    }
}
