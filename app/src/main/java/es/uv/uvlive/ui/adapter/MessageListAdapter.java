package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.uv.uvlive.data.gateway.response.MessageResponse;
import es.uv.uvlive.ui.adapter.viewholder.MessageViewHolder;

/**
 * Created by alextfos on 01/03/2017.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private ArrayList<MessageResponse> list;

    public MessageListAdapter(ArrayList<MessageResponse> list) {
        this.list = list;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MessageViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (position != RecyclerView.NO_POSITION) {
            holder.setText(list.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
