package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.atraverf.uvlive.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.presenter.MessagesPresenter;
import es.uv.uvlive.session.MessageModel;
import es.uv.uvlive.ui.actions.MessageActions;
import es.uv.uvlive.ui.adapter.MessageListAdapter;

public class MessageListFragment extends BaseFragment implements MessageActions {

    public static final String ARG_ITEM_ID = "item_id";

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.fragmetn_message_list_editor)
    protected EditText mEditText;

    private MessagesPresenter messagesPresenter;
    private MessageListAdapter messageListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int idConversation;

    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    public static MessageListFragment newInstance(int id) {
        Bundle arguments = new Bundle();

        arguments.putInt(MessageListFragment.ARG_ITEM_ID, id);
        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void onResume() {
        super.onResume();
        UVLiveApplication.subscribeActions(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        UVLiveApplication.unsubscribeActions(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(getActivity()));
        scrollRecyclerViewControl();
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            idConversation = getArguments().getInt(ARG_ITEM_ID);
            messagesPresenter = new MessagesPresenter(idConversation,this);
            messagesPresenter.getMessages();
        }
    }

    private void scrollRecyclerViewControl() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        messagesPresenter.getMessages();
                    }
                }
            }
        });
    }

    @Override
    public void onMessagesReceived(List<MessageModel> messageModelList) {
        messageListAdapter = new MessageListAdapter(messageModelList);
        mRecyclerView.setAdapter(messageListAdapter);
        messageListAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(messageModelList.size()-1);
    }

    @Override
    public void getMessages() {
        messagesPresenter.getMessages(false);
    }

    @OnClick(R.id.fragment_message_list_send)
    public void onSendClicked() {
        messagesPresenter.sendMessage(idConversation,mEditText.getText().toString());
        mEditText.setText("");
    }
}
