package com.glsi.mesrecettes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private List<User> mUserList;
    private OnItemClickListener mListener;

    public UserAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mUserList = new ArrayList<>();
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void setUsers(List<User> userList) {
        mUserList = userList;
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewEmail;
        public TextView textViewName;
        public TextView textViewRole;

        private User mUser;

        public UserViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewRole = itemView.findViewById(R.id.text_view_role);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mUser);
                    }
                }
            });
        }

        public void bind(User user) {
            mUser = user;
            textViewEmail.setText(user.getEmail());
            textViewName.setText(user.getName());
            textViewRole.setText(user.getRole());
        }
    }
}
