package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.example.androidjokes.utils.ApplicationConstants;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Button buttonJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root=  inflater.inflate(R.layout.fragment_main, container, false);

        buttonJoke = (Button) root.findViewById(R.id.button_joke);

        buttonJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchJokeActivity();
            }
        });
        return root;

    }

    private void launchJokeActivity() {
        try {
            String data = new EndpointsAsyncTask().execute(getActivity().getApplicationContext()).get();
            //TODO remove log
            //Log.d("Anandhi", "Anandhi" + data);

            Intent intent = new Intent(getActivity().getApplicationContext(), com.example.androidjokes.JokeActivity.class);
            intent.putExtra(ApplicationConstants.JOKE, data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
