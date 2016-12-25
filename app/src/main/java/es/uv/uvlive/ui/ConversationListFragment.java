package es.uv.uvlive.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.atraverf.uvlive.R;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.presenter.ConversationsPresenter;
import es.uv.uvlive.ui.actions.ConversationsActions;
import es.uv.uvlive.ui.adapter.ConversationsAdapter;
import es.uv.uvlive.ui.adapter.ListContentManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ConversationListFragment extends BaseFragment implements ConversationsAdapter.OnConversationItemClick, ConversationsActions {

    private ArrayAdapter<ListContentManager.ListItem> mArrayAdapter;
    private ConversationsListResponse conversationsListResponse;
    @InjectView(R.id.fragment_conversation_list_rl)
    RecyclerView recyclerView;

    public static ConversationListFragment newInstance() {
        return new ConversationListFragment();
    }

    public ConversationListFragment() {
    }

    private void initalizeList(ConversationsListResponse conversationsListResponse) {
        Log.d("proves", "Conversaciones - Vuelta del servidor");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ConversationsAdapter(conversationsListResponse,this));
        this.conversationsListResponse = conversationsListResponse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversation_list, container, false);
        ButterKnife.inject(this,rootView);
        initializePresenter();
        return rootView;
    }

    private void initializePresenter() {
        ConversationsPresenter conversationsPresenter = new ConversationsPresenter(this);
        conversationsPresenter.getConversations();
    }

    @Override
    public void onConversationItemClicked(int position) {
        if (getActivity() instanceof  MainActivity) {
            ((MainActivity)getActivity()).onItemSelected(conversationsListResponse.getConversations().get(position).getId());
        }
    }

    @Override
    public void onConversationsReceived(ConversationsListResponse conversationsListResponse) {
        initalizeList(conversationsListResponse);
    }
}
