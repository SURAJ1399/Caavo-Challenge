package net.cavvo.cavvochallenge.presenter;

import android.os.AsyncTask;

import net.cavvo.cavvochallenge.CartActivity;
import net.cavvo.cavvochallenge.contract.CartActivityview;
import net.cavvo.cavvochallenge.contract.CartPresenter;
import net.cavvo.cavvochallenge.room.DatabaseClient;
import net.cavvo.cavvochallenge.room.CartEntity;

import java.util.List;

public class CartActivityPresenter implements CartPresenter {
   CartActivityview cartActivityview;
   CartActivity cartActivity;

    public CartActivityPresenter(CartActivityview cartActivityview ,CartActivity cartActivity) {
        this.cartActivityview=cartActivityview;
        this.cartActivity=cartActivity;
    }

    @Override
    public void getdatafromroom() {
   getTasks();
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<CartEntity>> {

            @Override
            protected List<CartEntity> doInBackground(Void... voids) {
                List<CartEntity> cartEntityList = DatabaseClient
                        .getInstance(cartActivity)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return cartEntityList;
            }

            @Override
            protected void onPostExecute(List<CartEntity> tasks) {
                super.onPostExecute(tasks);
                cartActivityview.setrecycleradapter(tasks);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
