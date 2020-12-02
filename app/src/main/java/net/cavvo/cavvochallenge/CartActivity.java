package net.cavvo.cavvochallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import net.cavvo.cavvochallenge.adapter.CartAdapter;
import net.cavvo.cavvochallenge.contract.CartActivityview;
import net.cavvo.cavvochallenge.presenter.CartActivityPresenter;
import net.cavvo.cavvochallenge.room.CartEntity;

import java.util.List;

public class CartActivity extends AppCompatActivity  implements CartActivityview {
        CartActivityPresenter cartActivityPresenter;
        SpotsDialog spotsDialog;

       RecyclerView recyclerView;
       CartAdapter recpieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recyclerview);
        spotsDialog=new SpotsDialog(CartActivity.this);
        cartActivityPresenter=new CartActivityPresenter(CartActivity.this,CartActivity.this);
        requestdatafromroom();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void requestdatafromroom() {
        cartActivityPresenter.getdatafromroom();

    }

    @Override
    public void setrecycleradapter(final List<CartEntity> cartEntities) {

        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);


                recyclerView.setLayoutManager(layoutManager);
                recpieAdapter = new CartAdapter(cartEntities);
                recyclerView.setAdapter(recpieAdapter);
                recpieAdapter.notifyDataSetChanged();
            }
        });


    }
}