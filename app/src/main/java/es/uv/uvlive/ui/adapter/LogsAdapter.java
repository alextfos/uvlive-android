package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.data.gateway.response.LogResponse;
import es.uv.uvlive.ui.adapter.viewholder.LogViewHolder;

/**
 * Created by atraver on 22/03/17.
 */

public class LogsAdapter extends RecyclerView.Adapter<LogViewHolder> {
    private final LogListResponse logListResponse;

    public LogsAdapter(LogListResponse logListResponse) {
        this.logListResponse = logListResponse;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return LogViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        LogResponse logResponse = logListResponse.getLogs().get(position);
        holder.setLevel(logResponse.getLevel());
        holder.setClazz(logResponse.getClazz());
        holder.setTimeStamp(logResponse.getTimeStamp());
        holder.setMessage(logResponse.getMessage());
    }

    @Override
    public int getItemCount() {
        return logListResponse.getLogs().size();
    }
}
