package com.example.first;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyViewModel extends ViewModel {
    OkHttpClient client = new OkHttpClient();
    String url ="https://catfact.ninja/fact";
    Request request = new Request.Builder().url(url).build();

    public void getFact(final OnFactGetListener listener) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                        JSONObject mess = new JSONObject(myResponse);

                        String fact = mess.getString("fact");
//                         Log.d("Myactivity", "onResponse: "+fact);
                        int length = mess.getInt("length");
                        final String message = fact+" " +length;

                        listener.onFactGet(message);

//                        listener.OnFactget();
//                        Myactivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mtw.setText(message);
//                            }
//                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            }

        });
    }

    private MutableLiveData<String> Data;

    public MutableLiveData<String> getData() {
        if (Data == null) {
            Data = new MutableLiveData<String>();
        }
        return Data;
    }

    interface OnFactGetListener {
        public void onFactGet(String fact);
    }



}

