package com.example.poker101;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Log_In_Fragment_App_Name extends Fragment {


    public Log_In_Fragment_App_Name() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log__in__fragment__app__name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_log_in = getActivity().findViewById(R.id.btn_log_in);

        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_log_in_facebook = new Facebook_Log_In_Fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_log_in, fragment_log_in_facebook, "fragment_log_in_facebook").addToBackStack(null).commit();
            }
        });
    }
}
