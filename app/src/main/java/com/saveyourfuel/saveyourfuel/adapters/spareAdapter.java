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
import com.saveyourfuel.saveyourfuel.models.spareCard;

import java.util.ArrayList;

/**
 * Created by root on 22/3/18.
 */

public class spareAdapter extends RecyclerView.Adapter<spareAdapter.MyViewHolder>{

    private ArrayList<spareCard> cards;
    Context context;


    public spareAdapter(ArrayList<spareCard> cards, Context context){
        this.cards = cards;
        this.context=context;
    }

    @Override
    public spareAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spare_card,parent,false);
        return new spareAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(spareAdapter.MyViewHolder holder, int position) {

        holder.Title.setText(cards.get(position).title);
        holder.description.setText(cards.get(position).description);
        holder.price.setText(cards.get(position).price);
        holder.imageView.setImageBitmap(cards.get(position).thumbnail);


    }

    public void addNewCard(spareCard item){
        cards.add(item);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, description, price;
        public ImageView imageView;
        public RelativeLayout my_card;

        public MyViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.icon_for_recycle);
            my_card = itemView.findViewById(R.id.card);

            my_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    spareCard tmp = cards.get(position);
                    Intent i = new Intent("com.saveyourfuel.saveyourfuel.spareOwnerDetails");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("name",tmp.name);
                    i.putExtra("phone",tmp.phone);
                    i.putExtra("spare_name",tmp.sparePartName);
                    i.putExtra("description",tmp.description);
                    i.putExtra("p_id",tmp.p_id);
                    i.putExtra("price",tmp.price);
                    context.startActivity(i);
                }
            });

        }
    }
}
