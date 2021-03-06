package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import es.uv.uvlive.ui.adapter.ConversationsAdapter;

import butterknife.ButterKnife;

/**
 * Created by alextfos on 29/11/2016.
 */

public class ConversationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_conversation_title)
    TextView titleTv;
    @BindView(R.id.item_conversation_container)
    ViewGroup container;

    public static ConversationViewHolder newInstance(ViewGroup parent,
                  ConversationsAdapter.OnConversationItemClick onConversationItemClick) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new ConversationViewHolder(view,onConversationItemClick);
    }

    public ConversationViewHolder(View itemView,
                                  final ConversationsAdapter.OnConversationItemClick onConversationItemClick) {
        super(itemView);
        ButterKnife.bind(this,itemView);
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

    public void setTitle(String title) {
        titleTv.setText(title);
    }
}
