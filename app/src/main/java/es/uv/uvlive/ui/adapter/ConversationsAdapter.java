package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import es.uv.uvlive.session.Conversation;
import es.uv.uvlive.ui.adapter.viewholder.ConversationViewHolder;
import es.uv.uvlive.ui.models.ConversationModel;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationViewHolder> {

    private List<ConversationModel> conversationModelList;
    private OnConversationItemClick onConversationItemClick;

    public ConversationsAdapter(List<ConversationModel> conversationList,
                                OnConversationItemClick onConversationItemClick) {
        this.conversationModelList = conversationList;
        this.onConversationItemClick = onConversationItemClick;
    }
    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ConversationViewHolder.newInstance(parent,onConversationItemClick);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        if (position != RecyclerView.NO_POSITION) {
            holder.decorate(conversationModelList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return conversationModelList.size();
    }

    public interface OnConversationItemClick {
        void onConversationItemClicked(int position);
    }
}
