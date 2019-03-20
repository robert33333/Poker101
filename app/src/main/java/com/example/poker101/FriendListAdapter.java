package com.example.poker101;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewWrapper> {
    JSONArray list_friend;
    public FriendListAdapter( JSONArray list_friend) {
        this.list_friend=list_friend;
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_friend, null);
        // create ViewHolder
        return new ViewWrapper(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewWrapper viewWrapper, int position) {
        try {
            viewWrapper.getFriend_name().setText(list_friend.getJSONObject(viewWrapper.getAdapterPosition()).getString("name"));
            Picasso.get().load("http://graph.facebook.com/"+list_friend.getJSONObject(viewWrapper.getAdapterPosition()).get("id")+"/picture?type=large").into(viewWrapper.getFriend_img());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_friend.length();
    }

    class ViewWrapper extends RecyclerView.ViewHolder {
        final View base;
        //TextView tv_name;

        AppCompatTextView friend_name;
        AppCompatImageView friend_img;

        public ViewWrapper(View itemView) {
            super(itemView);
            base = itemView;
        }

        AppCompatTextView getFriend_name() {
            if (friend_name == null) {
                friend_name = base.findViewById(R.id.friend_name);
            }
            return (friend_name);
        }

        AppCompatImageView getFriend_img() {
            if (friend_img == null) {
                friend_img = base.findViewById(R.id.friend_img);
            }
            return (friend_img);
        }
    }
}