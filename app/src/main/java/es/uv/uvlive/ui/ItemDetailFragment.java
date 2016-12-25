package es.uv.uvlive.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atraverf.uvlive.R;
import es.uv.uvlive.ui.adapter.ListContentManager;


public class ItemDetailFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ListContentManager.ListItem mItem;
    //@InjectView(R.id.recycler_view)
    //private RecyclerView mRecyclerView;

    public static ItemDetailFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, id);
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public ItemDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        /*MessagesForm messagesForm = new MessagesForm();
        messagesForm.setIdConversation(mItem.getIdConversation());

        Response.Listener<MessagesResponse> responseListener = new Response.Listener<MessagesResponse>(){
            @Override
            public void onResponse(MessagesResponse messagesResponse) {
                Log.d("proves", "vuelta del servidor");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves","Error de respuesta en el login");
                Toast.makeText(UVLiveApplication.getInstance(), "Ha habido un problema con el login", Toast.LENGTH_LONG).show();
            }
        };

        UVLiveApplication.getUVLiveGateway().messages(messagesForm,responseListener,errorListener);*/

    }
}
