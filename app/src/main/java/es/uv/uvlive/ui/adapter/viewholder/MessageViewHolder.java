package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_message_tv)
    protected TextView textTv;

    @BindView(R.id.item_message_feedback)
    protected ImageView feedbackIv;

    public static MessageViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setFeedback(boolean sended) {
        if (sended) {
            feedbackIv.setImageDrawable(ContextCompat.getDrawable(feedbackIv.getContext(), R.drawable.ic_cloud_done_white_24dp));
        } else {
            feedbackIv.setImageDrawable(ContextCompat.getDrawable(feedbackIv.getContext(), R.drawable.ic_cloud_off_white_24dp));
        }
    }

    public void setText(String text) {
        textTv.setText(text);
    }
}
