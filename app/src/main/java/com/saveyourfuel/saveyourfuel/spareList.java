package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saveyourfuel.saveyourfuel.adapters.spareAdapter;
import com.saveyourfuel.saveyourfuel.adapters.spareListAdapter;
import com.saveyourfuel.saveyourfuel.adapters.truckListAdapter;
import com.saveyourfuel.saveyourfuel.models.card;
import com.saveyourfuel.saveyourfuel.models.spareCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class spareList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    Toolbar toolbar;
    String name,ph;
    SwipeRefreshLayout swipeRefreshLayout;
    spareListAdapter adapter;

    ArrayList<spareCard> cards = new ArrayList<>();
    RecyclerView itemViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_list);

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sharedPref.getString("Name","");
        ph = sharedPref.getString("ph","");

        setView();
        loadSpareDetails();

    }


    void setView(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your trucks on sale");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spareList.this.finish();
            }
        });


        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        itemViewHolder = findViewById(R.id.item_viewholder);
        itemViewHolder.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        adapter = new spareListAdapter(cards,this) ;
        itemViewHolder.setAdapter(adapter);
    }

    void loadSpareDetails(){
        swipeRefreshLayout.setRefreshing(true);
        //cards.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        cards.clear();
        adapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        String url = "http://139.59.29.124:3000/spare-with-id?id="+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for( int i=0;i<jsonArray.length();i++){
                        JSONObject tmp = jsonArray.getJSONObject(i);
                        String imageString = tmp.getString("image");
                        Bitmap spareImage=null;
                        if (!imageString.isEmpty()) {
                            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                            spareImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        }

                        cards.add(new spareCard("On Sale", tmp.getString("description"),tmp.getString("price"),spareImage,
                                tmp.getString("p_id"),
                                tmp.getString("seller_name"),
                                tmp.getString("phone"),
                                tmp.getString("spare_part")));
                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Snackbar.make(findViewById(android.R.id.content), R.string.check_your_connection, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        loadSpareDetails();
    }


}
