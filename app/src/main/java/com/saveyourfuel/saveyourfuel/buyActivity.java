package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saveyourfuel.saveyourfuel.adapters.cardAdapter;
import com.saveyourfuel.saveyourfuel.adapters.spareAdapter;
import com.saveyourfuel.saveyourfuel.models.card;
import com.saveyourfuel.saveyourfuel.models.spareCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class buyActivity extends AppCompatActivity implements  View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    Toolbar toolbar;
    String name,ph;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton spare, fuel,truckSell;
    cardAdapter truckAdapter;
    spareAdapter sparePartsAdapter;

    ArrayList<card> truckCards = new ArrayList<>();
    ArrayList<spareCard> spareCards = new ArrayList<>();
    RecyclerView itemViewHolder;

    Context context;

    int whichActive =1;
    //1 for trucks 2 for spare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

//        cards.add(new card("On sale","Ashoka layland", "1,00,000", null));

        toolbar = findViewById(R.id.toolbar_buy);

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sharedPref.getString("Name","");
        ph = sharedPref.getString("ph","");

        context = getApplicationContext();

        setView();
        loadTruckDetails();
        //Snackbar.make(findViewById(android.R.id.content),R.string.coming_soon,Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sell_truck:

                Intent i = new Intent(buyActivity.this, sellTruckForm.class);

                startActivity(i);
                buyActivity.this.finish();
                break;

            case R.id.sell_spare:

                Intent i1 = new Intent(buyActivity.this, sell_spare_partsActivity.class);

                startActivity(i1);
                buyActivity.this.finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    void setView(){


        toolbar.setTitle(R.string.buy);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(R.string.name);

        truckSell = findViewById(R.id.truck_sell_button);
        truckSell.setOnClickListener(this);

        spare = findViewById(R.id.spare_parts);
        spare.setOnClickListener(this);

        fuel = findViewById(R.id.fuel_button);
        fuel.setOnClickListener(this);

        itemViewHolder = findViewById(R.id.item_viewholder);
        itemViewHolder.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        truckAdapter = new cardAdapter(truckCards,this);
        itemViewHolder.setAdapter(truckAdapter);

        sparePartsAdapter = new spareAdapter(spareCards,context);

        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(buyActivity.this,home.class);
                startActivity(i);
                buyActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.buy_toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(buyActivity.this,home.class);
        startActivity(i);
        buyActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.truck_sell_button:
                loadTruckDetails();
                break;

            case R.id.spare_parts:
                loadSparePartsDetails();
                break;

            case R.id.fuel_button:
                Snackbar.make(view,R.string.coming_soon,Snackbar.LENGTH_LONG).show();
                break;
        }
    }


    void loadSparePartsDetails(){
        itemViewHolder.setAdapter(sparePartsAdapter);
        whichActive=2;
        getSupportActionBar().setTitle("Buy spare parts");
        spareCards.clear();
        sparePartsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue queue = Volley.newRequestQueue(this);


        String url = "http://139.59.29.124:3000/all-spare?limit=" + 10 + "&offset=" + 0;
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
                        Log.d("response",tmp.toString());

                        spareCards.add(new spareCard("On Sale", tmp.getString("description"),tmp.getString("price"),spareImage,
                                tmp.getString("p_id"),
                                tmp.getString("seller_name"),
                                tmp.getString("phone"),
                                tmp.getString("spare_part")));

                    }
                    sparePartsAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeRefreshLayout.setRefreshing(false);
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
    void loadTruckDetails(){
        itemViewHolder.setAdapter(truckAdapter);
        whichActive = 1;
        getSupportActionBar().setTitle(R.string.banss);
        truckCards.clear();
        truckAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
        //truckCards.clear();
        RequestQueue queue = Volley.newRequestQueue(this);


        String url = "http://139.59.29.124:3000/all-products?limit=" + 10 + "&offset=" + 0;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for( int i=0;i<jsonArray.length();i++){
                        JSONObject tmp = jsonArray.getJSONObject(i);
                        String imageString = tmp.getString("image");
                        Bitmap truckImage=null;
                        if (!imageString.isEmpty()) {
                            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                            truckImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        }

                        truckCards.add(new card("On Sale", tmp.getString("company"),tmp.getString("price"),truckImage,
                                tmp.getString("p_id"),
                                tmp.getString("name"),
                                tmp.getString("phone")));
                    }
                    truckAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeRefreshLayout.setRefreshing(false);
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
        switch (whichActive){
            case 1:
                loadTruckDetails();
                break;
            case 2:
                loadSparePartsDetails();
                break;
        }

    }
}
