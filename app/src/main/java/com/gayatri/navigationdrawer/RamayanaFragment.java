package com.gayatri.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class RamayanaFragment extends Fragment {

    private  static final int REQUEST_CODE_QUIZ=1;
    private static final String SHARED_PREFS="sharedPrefs";
    private static final String HIGH_SCORE="keyHighScore";
    private TextView textVIewHighscore;
    private int highscore;
    public static final int QUIZ_CATEGORY=1;

    @Nullable
    @Override
    public View  onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_ramayana, container, false);

        Button btn_start_quiz = (Button)view.findViewById(R.id.ramayana_btn_quiz_start);

        btn_start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("QUIZ_CATEGORY",1);
                startActivity(intent);
            }
        });

return view;
    }

}
