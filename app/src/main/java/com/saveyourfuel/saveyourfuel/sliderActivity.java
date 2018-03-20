package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class sliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private  int[] laypuots;
    private TextView[] dots;
    private LinearLayout dotlayout;
    Button next,skip;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_slider);

        viewPager = findViewById(R.id.view_pager);
        dotlayout=findViewById(R.id.layputDots);
        skip=findViewById(R.id.btnBack);
        next = findViewById(R.id.btnNext);

        laypuots = new int[]{R.layout.screen1,R.layout.screen2,R.layout.screen3,R.layout.screen4,R.layout.screen5};

        addBottonDots(0);
        ChangestatusColor();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sliderActivity.this,loginActivity.class);
                startActivity(i);
                sliderActivity.this.finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(+1);
                if(current<laypuots.length){
                    viewPager.setCurrentItem(current);
                }else{
                    Intent i = new Intent(sliderActivity.this,loginActivity.class);
                    startActivity(i);
                    sliderActivity.this.finish();
                }
            }
        });
    }

    private  int getItem(int i)
    {
        return viewPager.getCurrentItem()+i;
    }

    public void addBottonDots(int position)
    {
        dots =new TextView[laypuots.length];
        int[] colorActive = getResources().getIntArray(R.array.dor_active);
        int[] colorINActive = getResources().getIntArray(R.array.dor_inactive);
        dotlayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorINActive[position]);
            dotlayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(colorActive[position]);
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottonDots(position);
            if (position == laypuots.length - 1)
            {
                next.setText("Finish");
                skip.setVisibility(View.INVISIBLE);
            }else{
                next.setText("Next");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public  void ChangestatusColor()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(laypuots[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return laypuots.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }

}
