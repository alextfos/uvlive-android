package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;

public class UserViewHolder extends BaseViewHolder {

    @BindView(R.id.item_user_name)
    protected TextView username;

    public static UserViewHolder newInstance(OnItemClickListener onClickListener,ViewGroup parent) {
        return new UserViewHolder(onClickListener,inflateView(parent, R.layout.item_user));
    }

    public UserViewHolder(final OnItemClickListener onClickListener, View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onClickListener.onItemClicked(getAdapterPosition());
                }
            }
        });
    }

    public void setText(String text) {
         username.setText(text);
    }
}
