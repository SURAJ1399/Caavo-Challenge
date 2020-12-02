package net.cavvo.cavvochallenge.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.cavvo.cavvochallenge.R;
import net.cavvo.cavvochallenge.room.CartEntity;
import net.cavvo.cavvochallenge.room.DatabaseClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter <CartAdapter.Viewholder> {
    List<CartEntity> recpieModels;
    Context context;

    public CartAdapter(List<CartEntity> recpieModels)
    {
        this.recpieModels=recpieModels;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);


        context = parent.getContext();
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position)
    {
       holder.delete.setVisibility(View.VISIBLE);

        holder.name.setText(recpieModels.get(position).getName());
        Glide.with(context).load(recpieModels.get(position).getImage()).into(holder.imageView);
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(recpieModels.get(position).getName(),recpieModels.get(position).getImage());
            }
        });

         holder.delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  deleteTask(recpieModels.get(position),position);


             }
         });

    }

    @Override
    public int getItemCount() {
        return recpieModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        View mview;
        ImageView imageView;
        TextView name;
        Button addtocart;
        Button delete;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            name=mview.findViewById(R.id.name);
            imageView=mview.findViewById(R.id.image);
            addtocart=mview.findViewById(R.id.addtobag);
            delete=mview.findViewById(R.id.delete);
        }
    }
    private void saveTask(final String name, final String image) {

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                CartEntity cartEntity = new CartEntity();
                cartEntity.setName(name);
                cartEntity.setImage(image);


                //adding to database
                DatabaseClient.getInstance(context).getAppDatabase()
                        .taskDao()
                        .insert(cartEntity);
                        return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(context, "Product Added To Cart", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }
    private void deleteTask(final CartEntity cartEntity, final int position) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context).getAppDatabase()
                        .taskDao()
                        .delete(cartEntity);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, recpieModels.get(position).getName()+" Deleted", Toast.LENGTH_LONG).show();
                recpieModels.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, recpieModels.size());
                notifyDataSetChanged();
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
