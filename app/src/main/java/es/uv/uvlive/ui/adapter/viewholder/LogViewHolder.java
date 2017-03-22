package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by atraver on 22/03/17.
 */

public class LogViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_log_level)
    protected TextView levelTv;

    @BindView(R.id.item_log_clazz)
    protected TextView clazzTv;

    @BindView(R.id.item_log_timeStamp)
    protected TextView timeStampTv;

    @BindView(R.id.item_log_message)
    protected TextView messageTv;

    public static LogViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new LogViewHolder(view);
    }

    public LogViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setLevel(String level) {
        this.levelTv.setText(level);
    }

    public void setClazz(String clazz) {
        this.clazzTv.setText(clazz);
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStampTv.setText(timeStamp);
    }

    public void setMessage(String message) {
        this.messageTv.setText(message);
    }
}
