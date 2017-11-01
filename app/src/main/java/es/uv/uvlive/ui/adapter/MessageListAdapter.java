package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import es.uv.uvlive.session.MessageModel;
import es.uv.uvlive.ui.adapter.viewholder.MessageViewHolder;

public class MessageListAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private List<MessageModel> list;

    public MessageListAdapter(List<MessageModel> list) {
        this.list = list;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MessageViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (position != RecyclerView.NO_POSITION) {
            holder.setText(list.get(position).getOwner(),list.get(position).getMessage(), ""+list.get(position).getDate());
            holder.setFeedback(list.get(position).isSent());
            holder.setOwner(list.get(position).isMine());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
