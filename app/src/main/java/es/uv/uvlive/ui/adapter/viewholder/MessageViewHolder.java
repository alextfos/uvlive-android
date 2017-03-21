package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by alextfos on 01/03/2017.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.item_message_tv)
    protected
    TextView textTv;

    public static MessageViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this,itemView);
    }

    public void setText(String text) {
        textTv.setText(text);
    }
}
