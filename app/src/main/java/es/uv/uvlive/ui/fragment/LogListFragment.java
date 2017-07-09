package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.presenter.LogsPresenter;
import es.uv.uvlive.ui.actions.LogsActions;
import es.uv.uvlive.ui.adapter.LogsAdapter;

public class LogListFragment extends BaseFragment implements LogsActions {

    @BindView(R.id.fragment_log_list_list)
    protected RecyclerView logList;

    public static LogListFragment newInstance() {
        LogListFragment fragment = new LogListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_log_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        logList.setLayoutManager(new LinearLayoutManager(getActivity()));
        LogsPresenter logsPresenter = new LogsPresenter(this);
        logsPresenter.getLogs();
    }

    @Override
    public void onLogsReceived(LogListResponse logListResponse) {
        logList.setAdapter(new LogsAdapter(logListResponse));
    }
}
