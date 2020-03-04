package com.example.myfirst;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> fact;

    public MutableLiveData<String> getCurrentName() {
        if (fact == null) {
            fact = new MutableLiveData<String>();
        }
        return fact;
    }

    public void getFacts() {

        OkHttpClient client = new OkHttpClient();

        String url = "https://catfact.ninja/fact";
        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String myResponse = response.body().string();
                JSONObject res = null;
                try {
                    res = new JSONObject(myResponse);

                    String facttxt = res.getString("fact");
                    fact.postValue(facttxt);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

}