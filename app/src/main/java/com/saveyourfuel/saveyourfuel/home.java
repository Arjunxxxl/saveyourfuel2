package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saveyourfuel.saveyourfuel.adapters.cardAdapter;
import com.saveyourfuel.saveyourfuel.models.card;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    String name,ph;
    TextView nameT,phT;
    Bitmap profileImage=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.mainViewHolder);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<card> cards = new ArrayList<>();
        cards.add(new card("Buy", "description", R.mipmap.buybag));
        cards.add(new card("Card", "description", R.mipmap.creditcard));
        cards.add(new card("Upload", "description", R.mipmap.uploaddoc));

        recyclerView.setAdapter(new cardAdapter(cards));

         Intent i = getIntent();
         name = i.getExtras().getString("Name", "");
         ph = i.getExtras().getString("ph", "");
         String imageString = i.getExtras().getString("image", "");
         if(!imageString.isEmpty()){
             byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
             profileImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
         }

        toolbar.setTitle("Save Your Fuel");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Your Profile");

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#00000000"));

        nameT = findViewById(R.id.user_name);
        phT = findViewById(R.id.phone_user);
        nameT.setText(name);
        phT.setText(ph);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutus:
                Intent i = new Intent(home.this,aboutusActivity.class);
                i.putExtra("Name",name);
                i.putExtra("ph",ph);
                startActivity(i);
                break;
            case R.id.setting:
                Intent i1 = new Intent(home.this,settingsActivity.class);
                i1.putExtra("Name",name);
                i1.putExtra("ph",ph);
                startActivity(i1);
                break;
            case R.id.money:
                Intent i2 = new Intent(home.this,balanceActivity.class);
                i2.putExtra("Name",name);
                i2.putExtra("ph",ph);
                startActivity(i2);
                break;
            case R.id.upload:
                Intent i4 = new Intent(home.this,uploadActivity.class);
                i4.putExtra("Name",name);
                i4.putExtra("ph",ph);
                startActivity(i4);
                break;

            case R.id.logout:
                startActivity(new Intent(home.this, loginActivity.class));
                this.finish();
                Toast.makeText(getApplicationContext(), "You have being logout", Toast.LENGTH_LONG).show();
                SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", " ");
                editor.putString("password", " ");
                editor.commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
