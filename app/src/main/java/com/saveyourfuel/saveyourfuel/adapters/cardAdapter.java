package com.saveyourfuel.saveyourfuel.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saveyourfuel.saveyourfuel.R;
import com.saveyourfuel.saveyourfuel.home;
import com.saveyourfuel.saveyourfuel.models.card;
import com.saveyourfuel.saveyourfuel.uploadActivity;

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
        public LinearLayout my_card;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.icon_for_recycle);
            my_card = itemView.findViewById(R.id.card1);

            my_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Snackbar.make(view, "Click detected on item " + position,
                            Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    switch (position)
                    {
                        case 0 :
                            Intent i2 = new Intent("com.saveyourfuel.saveyourfuel.documentStatus");

                            i2.putExtra("Name",home.name);
                            i2.putExtra("ph",home.ph);
                            i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i2);
                            break;
                        case 1:
                            Intent i3 = new Intent("com.saveyourfuel.saveyourfuel.balanceActivity");
                            i3.putExtra("Name",home.name);
                            i3.putExtra("ph",home.ph);
                            i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i3);
                            break;
                        case 2:
                            Intent i4 = new Intent("com.saveyourfuel.saveyourfuel.documentStatus");
                            i4.putExtra("Name",home.name);
                            i4.putExtra("ph",home.ph);
                            i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i4);

                            break;
                    }
                }
            });

        }
    }
}
