package com.saveyourfuel.saveyourfuel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saveyourfuel.saveyourfuel.R;
import com.saveyourfuel.saveyourfuel.models.card;

import java.util.ArrayList;

/**.
 * Created by root on 11/2/18.
 */

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.MyViewHolder>{
    private ArrayList<card> cards;

    public cardAdapter(ArrayList<card> cards){
        this.cards = cards;
    }

    @Override
    public cardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cardAdapter.MyViewHolder holder, int position) {

        holder.Title.setText(cards.get(position).title);
        holder.description.setText(cards.get(position).description);
        holder.imageView.setImageResource(cards.get(position).icon);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, description;
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.icon_for_recycle);


        }
    }
}
