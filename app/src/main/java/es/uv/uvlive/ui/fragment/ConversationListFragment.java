package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.atraverf.uvlive.R;

import java.util.List;

import butterknife.BindView;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.presenter.BasePresenter;
import es.uv.uvlive.presenter.ConversationsPresenter;
import es.uv.uvlive.ui.actions.ConversationsActions;
import es.uv.uvlive.ui.activity.MainActivity;
import es.uv.uvlive.ui.adapter.ConversationsAdapter;
import es.uv.uvlive.ui.models.ConversationModel;

public class ConversationListFragment extends BaseFragment implements ConversationsAdapter.OnConversationItemClick, ConversationsActions {

    private List<ConversationModel> conversationModelList;
    private ConversationsPresenter conversationsPresenter;

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
        super.onViewCreated(view,savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getTitle());
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return conversationsPresenter;
    }

    @Override
    protected void initializePresenter() {
        conversationsPresenter = new ConversationsPresenter(this);
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
        int idConversation = UVLivePreferences.getInstance().getDeeplinkParams();
        if (idConversation > 0) {
            UVLivePreferences.getInstance().removeDeeplinkParams();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity)getActivity()).onItemSelected(idConversation);
            }
        }
    }

    @Override
    public void getConversations() {
        initializePresenter();
    }
}
