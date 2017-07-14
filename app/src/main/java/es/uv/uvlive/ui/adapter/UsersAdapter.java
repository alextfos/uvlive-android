package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import es.uv.uvlive.session.UserModel;
import es.uv.uvlive.ui.adapter.viewholder.OnItemClickListener;
import es.uv.uvlive.ui.adapter.viewholder.UserViewHolder;

public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> implements OnItemClickListener {

    private List<UserModel> usersList;
    private OnUserClickListener onUserClickListener;

    public UsersAdapter(OnUserClickListener onUserClickListener, List<UserModel> usersList) {
        this.usersList = usersList;
        this.onUserClickListener = onUserClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserViewHolder.newInstance(this,parent);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.setText(usersList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    @Override
    public void onItemClicked(int position) {
        onUserClickListener.onUserClicked(usersList.get(position));
    }

    public interface OnUserClickListener {
        void onUserClicked(UserModel userModel);
    }
}
