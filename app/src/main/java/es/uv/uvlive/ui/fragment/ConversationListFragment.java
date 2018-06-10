package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.atraverf.uvlive.R;

import java.util.List;

import butterknife.BindView;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.presenter.ConversationsPresenter;
import es.uv.uvlive.session.Conversation;
import es.uv.uvlive.ui.actions.ConversationsActions;
import es.uv.uvlive.ui.activity.MainActivity;
import es.uv.uvlive.ui.adapter.ConversationsAdapter;
import es.uv.uvlive.ui.models.ConversationModel;

public class ConversationListFragment extends BaseFragment implements ConversationsAdapter.OnConversationItemClick, ConversationsActions {

    private List<ConversationModel> conversationModelList;

    @BindView(R.id.fragment_conversation_list_rl)
    RecyclerView recyclerView;

    public static ConversationListFragment newInstance() {
        return new ConversationListFragment();
    }

    public ConversationListFragment() {
    }

    private void initalizeList(List<ConversationModel> conversationModelList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ConversationsAdapter(conversationModelList,this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        this.conversationModelList = conversationModelList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_conversation_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initializePresenter();
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

    private void initializePresenter() {
        ConversationsPresenter conversationsPresenter = new ConversationsPresenter(this);
        conversationsPresenter.getLocalConversations();
        conversationsPresenter.updateConversations();
    }

    @Override
    public void onConversationItemClicked(int position) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).onItemSelected(conversationModelList.get(position).getId());
        }
    }

    @Override
    public void onConversationsReceived(List<ConversationModel> conversationModelList) {
        initalizeList(conversationModelList);
    }

    @Override
    public void getConversations() {
        initializePresenter();
    }
}
