package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.presenter.LogsPresenter;
import es.uv.uvlive.ui.actions.LogsActions;
import es.uv.uvlive.ui.adapter.ConversationsAdapter;
import es.uv.uvlive.ui.adapter.LogsAdapter;

/**
 * Created by atraver on 22/03/17.
 */

public class LogListFragment extends BaseFragment implements LogsActions {

    @BindView(R.id.fragment_log_list_list)
    protected RecyclerView logList;

    public static LogListFragment newInstance() {
        LogListFragment fragment = new LogListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_list, container, false);
        ButterKnife.bind(this,rootView);
        logList.setLayoutManager(new LinearLayoutManager(getActivity()));
        LogsPresenter logsPresenter = new LogsPresenter(this);
        logsPresenter.getLogs();
        return rootView;
    }

    @Override
    public void onLogsReceived(LogListResponse logListResponse) {
        logList.setAdapter(new LogsAdapter(logListResponse));
    }
}
