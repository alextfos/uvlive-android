package com.example.atraverf.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.atraverf.uvlive.data.gateway.response.ConversationsListResponse;
import com.example.atraverf.uvlive.ui.adapter.viewholder.ConversationViewHolder;

/**
 * Created by alextfos on 29/11/2016.
 */

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationViewHolder> {

    private ConversationsListResponse conversationsListResponse;
    private OnConversationItemClick onConversationItemClick;

    public ConversationsAdapter(ConversationsListResponse conversationsListResponse,
                                OnConversationItemClick onConversationItemClick) {
        this.conversationsListResponse = conversationsListResponse;
        this.onConversationItemClick = onConversationItemClick;
    }
    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ConversationViewHolder.newInstance(parent,onConversationItemClick);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        holder.setTitle(conversationsListResponse.getConversations().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return conversationsListResponse.getConversations().size();
    }

    public interface OnConversationItemClick {
        void onConversationItemClicked(int position);
    }
}
