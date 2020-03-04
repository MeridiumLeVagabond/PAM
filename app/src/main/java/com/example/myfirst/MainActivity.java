package com.example.myfirst;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private ProgressBar bar;
    private Button bt1;

    public MainActivity() throws JSONException {
    }

    private void callNetwork () {
        try {
            Thread.sleep(10_000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Logger logger = Logger.getLogger("MyLog");
        setContentView(R.layout.activity_main);
        bt1 = findViewById(R.id.button1);
        txt = (TextView)findViewById(R.id.text1);
        bar = (ProgressBar)findViewById(R.id.bar1);
        //final GetExample geturl = new GetExample();

        final MyViewModel viewm = new ViewModelProvider(this).get(MyViewModel.class);

        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.
                txt.setText(newName);
                bar.setVisibility(View.INVISIBLE);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewm.getCurrentName().observe(this, nameObserver);


        bt1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                txt.setText("You made it load!!!");
                bar.setVisibility(View.VISIBLE);
                viewm.getFacts();

/*
                OkHttpClient client = new OkHttpClient();

                String url = "https://catfact.ninja/fact";
                final Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt.setText("Failure");
                            }
                        });

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String myResponse = response.body().string();

                    // get the first result

                        JSONObject res = null;
                        try {
                            res = new JSONObject(myResponse);



                            final String test = res.getString("fact");


                            //List<String> list = new ArrayList<String>();
                            //JSONArray array = obj.getJSONArray("interests");
                            //for(int i = 0 ; i < array.length() ; i++){
                            //    list.add(array.getJSONObject(i).getString("interestKey"));
                            //}

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt.setText(test);
                                    bar.setVisibility(View.INVISIBLE);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                /*
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                /*Uri uri = Uri.parse("http://catfact.ninja/fact"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                */
                /*try {
                    geturl.run("https://catfact.ninja/fact");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                }
            });
        }


    /*public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.Timeid);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
    }

