package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import es.uv.uvlive.session.Message;
import es.uv.uvlive.ui.actions.MessageActions;
import es.uv.uvlive.ui.adapter.viewholder.MessageViewHolder;
import es.uv.uvlive.ui.models.MessageModel;

public class MessageListAdapter extends RecyclerView.Adapter<MessageViewHolder> implements MessageActions.Adapter {

    private List<MessageModel> messageModelList;
    private RecyclerView recyclerView;

    public MessageListAdapter(RecyclerView recyclerView) {
        this.messageModelList = new LinkedList<>();
        this.recyclerView = recyclerView;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MessageViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (position != RecyclerView.NO_POSITION) {
            holder.decorate(messageModelList.get(position));
        }
    }

    @Override
    public void addItemListToTop(List<MessageModel> messageModelList) {
        this.messageModelList.addAll(this.messageModelList.size(),messageModelList);
        notifyItemRangeInserted(this.messageModelList.size(),messageModelList.size());
    }

    @Override
    public void addItemListToBottom(List<MessageModel> messageModelList) {
        // Add elements to init of list, for that reason the position is zero
        this.messageModelList.addAll(0,messageModelList);
        notifyItemRangeInserted(0,messageModelList.size());
    }

    @Override
    public void addItemToBottom(MessageModel messageModel) {
        // Add elements to init of list, for that reason the position is zero
        this.messageModelList.add(0,messageModel);
        notifyItemRangeInserted(0,1);
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    @Override
    public void onMessagesReceived(List<MessageModel> messageList) {
        messageModelList = messageList;
        notifyDataSetChanged();
    }

    @Override
    public void onMessageChanged(MessageModel messageModel) {
        int pos = messageModelList.indexOf(messageModel);
        messageModelList.set(pos, messageModel);
        notifyItemChanged(pos);
    }


}
