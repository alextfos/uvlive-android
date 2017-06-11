package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.atraverf.uvlive.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    public MessageListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message_list, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
