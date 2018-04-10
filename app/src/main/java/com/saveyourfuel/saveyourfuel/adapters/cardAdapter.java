package com.saveyourfuel.saveyourfuel.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saveyourfuel.saveyourfuel.R;
import com.saveyourfuel.saveyourfuel.models.card;

import java.util.ArrayList;

/**.
 * Created by root on 11/2/18.
 */

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.MyViewHolder>{
    private ArrayList<card> cards;
    Context context;


    public cardAdapter(ArrayList<card> cards,Context context){
        this.cards = cards;
        this.context=context;
    }

    @Override
    public cardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cardAdapter.MyViewHolder holder, int position) {

        holder.Title.setText(cards.get(position).title);
        holder.company.setText(cards.get(position).company);
        holder.price.setText(cards.get(position).price);
        holder.imageView.setImageBitmap(cards.get(position).thumbnail);


    }

    public void addNewCard(card item){
        cards.add(item);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, company, price;
        public ImageView imageView;
        public RelativeLayout my_card;

        public MyViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            company = itemView.findViewById(R.id.spare_part_name);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.icon_for_recycle);
            my_card = itemView.findViewById(R.id.card);

            my_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

//                    Snackbar.make(view, "Click detected on item " + position,
//                            Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                    switch (position)
//                    {
//
//                    }
                    card tmp = cards.get(position);
                    Intent i = new Intent("com.saveyourfuel.saveyourfuel.truckOwnerDetails");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("name",tmp.name);
                    i.putExtra("phone",tmp.phone);
                    i.putExtra("company",tmp.company);
                    i.putExtra("p_id",tmp.p_id);
                    i.putExtra("price",tmp.price);
                    i.putExtra("distance",tmp.distance);
                    i.putExtra("model",tmp.model);
                    i.putExtra("condition",tmp.condition);
                    context.startActivity(i);
                }
            });

        }
    }
}
