package net.cavvo.cavvochallenge.presenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.cavvo.cavvochallenge.MainActivity;
import net.cavvo.cavvochallenge.contract.Mainactivityview;
import net.cavvo.cavvochallenge.contract.Presenterview;
import net.cavvo.cavvochallenge.model.RecpieModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivityPresenter implements Presenterview {
    Mainactivityview mainactivityview;
    public MainActivityPresenter(   Mainactivityview mainactivityview) {
        this.mainactivityview=mainactivityview;
    }

    @Override
    public void getdatafromapi(final String url) {
        mainactivityview.showProgress();
        final Handler handler=new Handler();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .method("GET", null)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();
                    Log.i("resp", json);
                    GsonBuilder gsonBuilder = new GsonBuilder();

                    Gson gson = gsonBuilder.create();

                    Type listType = new TypeToken<List<RecpieModel>>(){}.getType();
                    List<RecpieModel> recpieModels= gson.fromJson(json, listType);
                    mainactivityview.setrecycleradapter(recpieModels);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                         mainactivityview.hideProgress();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
