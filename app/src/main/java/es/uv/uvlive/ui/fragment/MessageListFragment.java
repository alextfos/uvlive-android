package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.atraverf.uvlive.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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

    private int id;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            id = getArguments().getInt(ARG_ITEM_ID);
            messagesPresenter = new MessagesPresenter(this);
            messagesPresenter.getMessages(id);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onMessagesReceived(List<MessageModel> messageModelList) {
        mRecyclerView.setAdapter(new MessageListAdapter(messageModelList));
    }

    @OnClick(R.id.fragment_message_list_send)
    public void onSendClicked() {
        messagesPresenter.sendMessage(id,mEditText.getText().toString());
    }
}
