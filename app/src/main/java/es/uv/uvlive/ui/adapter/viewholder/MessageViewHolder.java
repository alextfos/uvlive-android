package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageViewHolder extends BaseViewHolder {

    @BindView(R.id.item_message_owner)
    protected TextView ownerTv;

    @BindView(R.id.item_message_tv)
    protected TextView textTv;

    @BindView(R.id.item_message_feedback)
    protected ImageView feedbackIv;

    @BindView(R.id.item_message_date)
    protected TextView dateTv;

    @BindView(R.id.item_message_card)
    protected CardView card;

    public static MessageViewHolder newInstance(ViewGroup parent) {
        return new MessageViewHolder(inflateView(parent,R.layout.item_message));
    }

    public MessageViewHolder(View itemView) {
        super(itemView);
    }

    public void setFeedback(boolean sended) {
        if (sended) {
            feedbackIv.setImageDrawable(ContextCompat.getDrawable(feedbackIv.getContext(), R.drawable.ic_cloud_done_white_24dp));
        } else {
            feedbackIv.setImageDrawable(ContextCompat.getDrawable(feedbackIv.getContext(), R.drawable.ic_cloud_off_white_24dp));
        }
    }

    public void setOwner(boolean isMine) {
        if (isMine) {
            card.setCardBackgroundColor(ContextCompat.getColor(card.getContext(), R.color.uv_sent_message_blue));
        }
    }

    public void setText(String owner, String text, String date) {
        ownerTv.setText(owner);
        textTv.setText(text);
        dateTv.setText(date);
    }
}
