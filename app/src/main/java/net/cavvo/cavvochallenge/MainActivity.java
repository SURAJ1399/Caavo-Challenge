package net.cavvo.cavvochallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import net.cavvo.cavvochallenge.adapter.RecpieAdapter;
import net.cavvo.cavvochallenge.contract.Mainactivityview;
import net.cavvo.cavvochallenge.model.RecpieModel;
import net.cavvo.cavvochallenge.presenter.MainActivityPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements net.cavvo.cavvochallenge.contract.Mainactivityview {

    MainActivityPresenter mainActivityPresenter;
    RecyclerView recyclerView;
    RecpieAdapter recpieAdapter;

    String apiurl="https://s3-ap-southeast-1.amazonaws.com/he-public-data/reciped9d7b8c.json";
    SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recyclerview);
        spotsDialog=new SpotsDialog(MainActivity.this);
        mainActivityPresenter=new MainActivityPresenter(MainActivity.this);
        requestdatafromapi(apiurl);

        findViewById(R.id.gotocart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));

            }
        });
    }

    @Override
    public void showProgress() {
     spotsDialog.show();
    }

    @Override
    public void hideProgress() {
     spotsDialog.dismiss();
    }

    @Override
    public void requestdatafromapi(String url) {
        mainActivityPresenter.getdatafromapi(url);
    }

    @Override
    public void setrecycleradapter(final List<RecpieModel>recpieModels) {

        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);


                recyclerView.setLayoutManager(layoutManager);
                recpieAdapter = new RecpieAdapter(recpieModels);
                recyclerView.setAdapter(recpieAdapter);
                recpieAdapter.notifyDataSetChanged();
            }
        });

    }
}