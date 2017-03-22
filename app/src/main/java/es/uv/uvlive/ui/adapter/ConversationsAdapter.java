package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.session.ConversationModel;
import es.uv.uvlive.ui.adapter.viewholder.ConversationViewHolder;

/**
 * Created by alextfos on 29/11/2016.
 */

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationViewHolder> {

    private List<ConversationModel> conversationModelList;
    private OnConversationItemClick onConversationItemClick;

    public ConversationsAdapter(List<ConversationModel> conversationModelList,
                                OnConversationItemClick onConversationItemClick) {
        this.conversationModelList = conversationModelList;
        this.onConversationItemClick = onConversationItemClick;
    }
    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ConversationViewHolder.newInstance(parent,onConversationItemClick);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        holder.setTitle(conversationModelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return conversationModelList.size();
    }

    public interface OnConversationItemClick {
        void onConversationItemClicked(int position);
    }
}
