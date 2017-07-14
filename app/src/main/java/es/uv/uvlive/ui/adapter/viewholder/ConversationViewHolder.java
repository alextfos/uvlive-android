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

public class ConversationViewHolder extends BaseViewHolder {

    @BindView(R.id.item_conversation_title)
    TextView titleTv;
    @BindView(R.id.item_conversation_container)
    ViewGroup container;

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

    public void setTitle(String title) {
        titleTv.setText(title);
    }
}
