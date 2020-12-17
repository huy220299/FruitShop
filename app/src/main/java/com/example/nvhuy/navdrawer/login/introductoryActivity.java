package com.example.nvhuy.navdrawer.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nvhuy.navdrawer.Adapter.viewpager_adapter;
import com.example.nvhuy.navdrawer.MainActivity;
//import com.example.nvhuy.navdrawer.PrefManager;
import com.example.nvhuy.navdrawer.R;
import com.example.nvhuy.navdrawer.models.item_viewpager;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;
import java.util.List;



public class introductoryActivity extends AppCompatActivity {

    ViewPager viewPager;
     viewpager_adapter adapter;
    List<item_viewpager> models;
    TextView txtSkip;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();



    LinearLayout slideDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private PageIndicatorView pageIndicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Anhxa();

//        prefManager = new PrefManager(this);
//        if (!prefManager.isFirstTimeLaunch()) {
//            launchHomeScreen();
//            finish();
//        }

        //todo add item
        models = new ArrayList<>();
        models.add(new item_viewpager(R.mipmap.brochure, "Brochure", "Brochure is an informative paper document (often also used for advertising) that can be folded into a template"));
        models.add(new item_viewpager(R.mipmap.sticker, "Sticker", "Sticker is a type of label: a piece of printed paper, plastic, vinyl, or other material with pressure sensitive adhesive on one side"));
        models.add(new item_viewpager(R.mipmap.poster, "Poster", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
        models.add(new item_viewpager(R.mipmap.namecard, "Name card", "Business cards are cards bearing business information about a company or individual."));

        //todo create adapter
        adapter = new viewpager_adapter(models, this);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

//        indicator = findViewById(R.id.indicator);
//        indicator.setViewPager(viewPager);
//        adapter.registerDataSetObserver(indicator.getDataSetObserver());
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setAnimationType(AnimationType.WORM);

        //todo add color background
        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        //todo create Indicator
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.nonactive_dot);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            slideDotspanel.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.activedot);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
                if (position == (adapter.getCount() -1)) {
                    txtSkip.setText("OK");
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(txtSkip.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        viewPager = findViewById(R.id.viewPage);
        slideDotspanel = findViewById(R.id.SlideDots);
        txtSkip = findViewById(R.id.Skip);

    }

//    private void launchHomeScreen() {
//        prefManager.setFirstTimeLaunch(false);
//        startActivity(new Intent(firstActivity.this, MainActivity.class));
//        finish();
//    }

}
