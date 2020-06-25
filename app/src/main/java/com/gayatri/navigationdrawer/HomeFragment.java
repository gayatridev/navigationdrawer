package com.gayatri.navigationdrawer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View homeView= inflater.inflate(R.layout.fragment_home,container, false);
        Button ramayana_btn=homeView.findViewById(R.id.ramayana_btn);
        //CharSequence ramayana =ramayana_btn.getText();
        //Toast.makeText(getContext(),ramayana,Toast.LENGTH_SHORT).show();

        Button mahabharatha_btn=homeView.findViewById(R.id.mahabharatha_btn);

        ramayana_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"I am in ramayana ClickListener ",Toast.LENGTH_SHORT).show();

                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft= manager.beginTransaction();
                ft.replace(R.id.fragment_container,new RamayanaFragment()).commit();

            }
        });

        mahabharatha_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"I am in mahabharata ClickListener ",Toast.LENGTH_SHORT).show();

                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft= manager.beginTransaction();
                ft.replace(R.id.fragment_container,new MahabharathaFragment()).commit();
            }
        });



        //return inflater.inflate(R.layout.fragment_home, container, false);
        return homeView;


    }
}
