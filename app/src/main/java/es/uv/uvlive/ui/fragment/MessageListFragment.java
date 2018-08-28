package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.OnClick;
import es.uv.uvlive.presenter.BasePresenter;
import es.uv.uvlive.presenter.MessagesPresenter;
import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.ui.actions.MessageActions;
import es.uv.uvlive.ui.adapter.MessageListAdapter;

public class MessageListFragment extends BaseFragment implements MessageActions {

    public static final String ARG_ITEM_ID = "item_id";

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.fragmetn_message_list_editor)
    protected EditText mEditText;

    private MessagesPresenter messagesPresenter;
    private LinearLayoutManager linearLayoutManager;
    private int idConversation;

    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    public static MessageListFragment newInstance(int id) {
        MessageListFragment fragment = new MessageListFragment();
        Bundle arguments = new Bundle();

        arguments.putInt(MessageListFragment.ARG_ITEM_ID, id);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    protected void initializePresenter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(Boolean.TRUE);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        MessageListAdapter messageListAdapter = new MessageListAdapter(mRecyclerView);
        mRecyclerView.setAdapter(messageListAdapter);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            idConversation = getArguments().getInt(ARG_ITEM_ID);
            messagesPresenter = new MessagesPresenter(idConversation,this, messageListAdapter);
            messagesPresenter.init();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(messagesPresenter.getTitle());
        }

    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return messagesPresenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        scrollRecyclerViewControl();
        mEditText.requestFocus();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            idConversation = savedInstanceState.getInt(ARG_ITEM_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_ITEM_ID, idConversation);
    }

    private void scrollRecyclerViewControl() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !MessageListFragment.this.messagesPresenter.isEndOfListLoaded() &&
                        !MessageListFragment.this.messagesPresenter.isFetchingPreviousMessages()) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        messagesPresenter.onTopOfMessageList();
                    }
                }
            }
        });
    }

    @Override
    public int getIdConversation() {
        return idConversation;
    }

    @Override
    public void onErrorFetchingMessages(BusinessError errorGettingConversation) {
        onError(errorGettingConversation);
    }

    @OnClick(R.id.fragment_message_list_send)
    public void onSendClicked() {
        messagesPresenter.sendMessage(mEditText.getText().toString());
        mEditText.setText("");
    }
}
