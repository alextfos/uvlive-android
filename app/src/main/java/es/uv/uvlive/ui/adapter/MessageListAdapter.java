package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import es.uv.uvlive.session.Message;
import es.uv.uvlive.ui.adapter.viewholder.MessageViewHolder;
import es.uv.uvlive.ui.models.MessageModel;

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
            holder.decorate(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
