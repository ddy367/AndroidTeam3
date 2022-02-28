package com.bitc.myapppush.android_team3;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bitc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class k_Main extends AppCompatActivity {

    ViewFlipper vFllipper;

    BottomNavigationView bottomNavi;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.k_main);

//        바텀 네비게이션 이동
        bottomNavi = findViewById(R.id.bottom_navigation);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });



//        갤러리 - 이 달의 추천 도서
        Gallery gallery = findViewById(R.id.galleryBook);
        TextView textView = findViewById(R.id.tv1);

        k_MainGalleryAdapter galAdapter = new k_MainGalleryAdapter(this);
        gallery.setAdapter(galAdapter);


//        플리퍼 배너
        int images[] = {
                R.drawable.flipper1,
                R.drawable.flipper2,
                R.drawable.flipper3,
        };
        vFllipper = findViewById(R.id.image_slide);

        for(int image : images) {
            fllipperImages(image);
        }

    }

    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        vFllipper.addView(imageView);
        vFllipper.setFlipInterval(3000);
        vFllipper.setAutoStart(true);

        vFllipper.setInAnimation(this,android.R.anim.slide_in_left);
        vFllipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }

    /*액션 바 이동*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.k_bottom_navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tab1:
//                Intent intent1 = new Intent(getApplicationContext(), H_Map.class);
//                startActivity(intent1);
                break;
            case R.id.tab2:
//                Intent intent2 = new Intent(getApplicationContext(), H_Map.class);
//                startActivity(intent2);
                break;
            case R.id.tab3:
                Intent intent3 = new Intent(getApplicationContext(), k_Main.class);
                startActivity(intent3);
                break;
            case R.id.tab4:
//                Intent intent4 = new Intent(getApplicationContext(), k_Main.class);
//                startActivity(intent4);
                break;
            case R.id.tab5:
//                Intent intent5 = new Intent(getApplicationContext(), k_Main.class);
//                startActivity(intent5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*바텀 네비게이션 이동*/
    private void BottomNavigate(int item) {
        switch (item)
        {
            case R.id.tab1:
//                Intent intent1 = new Intent(getApplicationContext(), H_Map.class);
//                startActivity(intent1);
                break;
            case R.id.tab2:
                Intent intent2 = new Intent(getApplicationContext(), H_Map.class);
                startActivity(intent2);
                break;
            case R.id.tab3:
                Intent intent3 = new Intent(getApplicationContext(), k_Main.class);
                startActivity(intent3);
                break;
            case R.id.tab4:
//                Intent intent4 = new Intent(getApplicationContext(), k_Main.class);
//                startActivity(intent4);
                break;
            case R.id.tab5:
//                Intent intent5 = new Intent(getApplicationContext(), k_Main.class);
//                startActivity(intent5);
                break;
        }
    }
}