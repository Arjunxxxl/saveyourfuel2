package com.saveyourfuel.saveyourfuel.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saveyourfuel.saveyourfuel.R;
import com.saveyourfuel.saveyourfuel.models.card;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 20/3/18.
 */

public class truckListAdapter extends RecyclerView.Adapter<truckListAdapter.MyViewHolder>{
    private ArrayList<card> cards;
    Context context;



    public truckListAdapter(ArrayList<card> cards,Context context){
        this.cards = cards;
        this.context=context;
    }

    @Override
    public truckListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new truckListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(truckListAdapter.MyViewHolder holder, int position) {

        holder.Title.setText("Your truck for sale!!");
        holder.company.setText(cards.get(position).company);
        holder.price.setText(cards.get(position).price);
        holder.imageView.setImageBitmap(cards.get(position).thumbnail);


    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
         TextView Title, company, price;
         ImageView imageView;
         RelativeLayout my_card;

         MyViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            company = itemView.findViewById(R.id.company);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.icon_for_recycle);
            my_card = itemView.findViewById(R.id.card);

            my_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    final card tmp = cards.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you really want to delete the post!!");
                    builder.setTitle("Remove sale post");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    deletePost(tmp.p_id,position);

                                }
                            });

                    builder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            });

        }

        void deletePost(String p_id, final int position){
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Removing the post!");
            progressDialog.show();

            RequestQueue queue = Volley.newRequestQueue(context);


            String url = "http://139.59.29.124:3000/delete-product?p_id="+p_id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        if(!jsonObject.getString("code").contentEquals("777")){

                            Toast.makeText(context,"Can't delete!",Toast.LENGTH_SHORT).show();

                        }else{
                            cards.remove(position);
                            notifyDataSetChanged();
                        }
                        progressDialog.hide();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,"Some error occurred!",Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error getting response", error.toString());
                    Toast.makeText(context,R.string.check_your_connection,Toast.LENGTH_SHORT).show();
                    progressDialog.hide();

                }
            });
            queue.add(stringRequest);
        }
    }




}
