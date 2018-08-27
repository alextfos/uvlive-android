package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import es.uv.uvlive.ui.models.MessageModel;

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

    public void decorate(MessageModel messageModel) {
        feedbackIv.setImageDrawable(ContextCompat.getDrawable(feedbackIv.getContext(), messageModel.isSended()?R.drawable.ic_cloud_done_white_24dp:R.drawable.ic_cloud_off_white_24dp));
        if (messageModel.isMine()) {
            card.setCardBackgroundColor(ContextCompat.getColor(card.getContext(), R.color.uv_sent_message_blue));
            feedbackIv.setVisibility(View.VISIBLE);
        } else {
            card.setCardBackgroundColor(ContextCompat.getColor(card.getContext(), android.R.color.white));
            feedbackIv.setVisibility(View.INVISIBLE);
        }
        ownerTv.setText(messageModel.getOwner());
        textTv.setText(messageModel.getMessage());
        dateTv.setText(messageModel.getDate());
    }
}
