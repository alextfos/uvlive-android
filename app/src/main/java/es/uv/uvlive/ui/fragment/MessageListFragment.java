package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.MessageListResponse;
import es.uv.uvlive.ui.adapter.ListContentManager;
import es.uv.uvlive.ui.adapter.MessageListAdapter;


public class MessageListFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ListContentManager.ListItem mItem;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static MessageListFragment newInstance(long id) {
        Bundle arguments = new Bundle();
        arguments.putLong(MessageListFragment.ARG_ITEM_ID, id);
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
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = ListContentManager.getItemMap().get(getArguments().getString(ARG_ITEM_ID));
            /*
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }*/
        }

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Se consultan los últimos mensajes almacenados y se solicita el resto con
        //una llamada al servicio
        final MessagesForm messagesForm = new MessagesForm();
//        messagesForm.setIdConversation(mItem.getIdConversation());

        Response.Listener<MessageListResponse> responseListener = new Response.Listener<MessageListResponse>(){
            @Override
            public void onResponse(MessageListResponse messagesResponse) {
                mRecyclerView.setAdapter(new MessageListAdapter(messagesResponse.getMessageResponse()));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                Log.d("proves", "vuelta del servidor");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves","Error de respuesta en el login");
                Toast.makeText(getContext(), "Ha habido un problema con el login", Toast.LENGTH_LONG).show();
            }
        };

        UVLiveApplication.getUVLiveGateway().messages(messagesForm,responseListener,errorListener);
    }
}
