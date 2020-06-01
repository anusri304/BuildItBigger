package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.androidjokes.utils.ApplicationConstants;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * This AsyncTask loads joke from the Google Cloud Endpoints Server
 */

public class EndpointsAsyncTask extends AsyncTask<Context, Integer, String> {
    private static MyApi myApiService = null;
    private Context context;
    int count = 0;
    ProgressBar progressBar;

    public EndpointsAsyncTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected String doInBackground(Context... contexts) {
//        for (; count <= 10; count++) {
//            try {
//                Thread.sleep(1000);
//                publishProgress(count);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(com.udacity.gradle.builditbigger.utils.ApplicationConstants.URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        this.context = contexts[0];

        try {
            // Invoke GCE to get the joke
            return myApiService.sayJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        Log.d("onPostExecute", "onPostExecute" + result);
        progressBar.setVisibility(View.GONE);
        if (!result.contains("failed")) {
            Intent intent = new Intent(context, com.example.androidjokes.JokeActivity.class);
            intent.putExtra(ApplicationConstants.JOKE, result);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("onPostExecute1", "onPostExecute1");
            context.startActivity(intent);
        } else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        progressBar.setProgress(values[0]);
//    }
}