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
import es.uv.uvlive.ui.adapter.ConversationsAdapter;

import butterknife.ButterKnife;
import es.uv.uvlive.ui.models.ConversationModel;

public class ConversationViewHolder extends BaseViewHolder {

    @BindView(R.id.item_conversation_title)
    protected TextView titleTv;
    @BindView(R.id.item_conversation_icon)
    protected ImageView iconIv;
    @BindView(R.id.item_conversation_container)
    protected ViewGroup container;

    public static ConversationViewHolder newInstance(ViewGroup parent,
                  ConversationsAdapter.OnConversationItemClick onConversationItemClick) {
        return new ConversationViewHolder(inflateView(parent,R.layout.item_conversation),onConversationItemClick);
    }

    public ConversationViewHolder(View itemView,
                                  final ConversationsAdapter.OnConversationItemClick onConversationItemClick) {
        super(itemView);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onConversationItemClick.onConversationItemClicked(getAdapterPosition());
                }
            }
        });
    }

    public void decorate(ConversationModel conversationModel) {
        titleTv.setText(conversationModel.getName());
        if (conversationModel.isGrouped()) {
            iconIv.setImageDrawable(ContextCompat.getDrawable(iconIv.getContext(), R.drawable.ic_grouped_conversation));
        } else {
            iconIv.setImageDrawable(ContextCompat.getDrawable(iconIv.getContext(), R.drawable.ic_single_conversation));
        }
    }
}
