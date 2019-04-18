package com.example.poker101;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.poker101.date.Comanda;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.poker101.User.oos;


/**
 * A simple {@link Fragment} subclass.
 */
public class Play_Response_Fragment extends Fragment {


    public Play_Response_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play__response_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_accept = getActivity().findViewById(R.id.btn_accept);

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Comanda comanda = new Comanda("acceptInvite",User.currentOpponent);
                            User.oos.writeObject(comanda);
                            User.goToGame();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception exp) {
                            exp.printStackTrace();
                        }

                    }


                    ;
                };

                Thread myThread = new Thread(myRunnable);
                myThread.start();
                try {
                    myThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
