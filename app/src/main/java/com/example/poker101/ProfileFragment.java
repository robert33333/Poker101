package com.example.poker101;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatTextView profile_name = getActivity().findViewById(R.id.profile_name);
        AppCompatImageView profile_img = getActivity().findViewById(R.id.profile_img);

        try {
            Picasso.get().load("http://graph.facebook.com/"+User.user.get("id")+"/picture?type=large").into(profile_img);
            profile_name.setText(User.user.getString("name"));


            JSONArray list_friend = User.user.getJSONObject("friends").getJSONArray("data");
            RecyclerView rv_test = (RecyclerView) view.findViewById(R.id.rv_friend_list);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            rv_test.setLayoutManager(mLinearLayoutManager);
            rv_test.setHasFixedSize(true);
            rv_test.setItemAnimator(new DefaultItemAnimator());

            //Set Adapter

            FriendListAdapter mAdapter = new FriendListAdapter(list_friend);
            rv_test.setAdapter(mAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
