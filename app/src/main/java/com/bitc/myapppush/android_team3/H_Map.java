package com.bitc.myapppush.android_team3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class H_Map extends AppCompatActivity {

    ImageButton ibtnYk;

    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_map);
//        setTitle("T3 도서 리뷰 커뮤니티");
//        액션바 뒤로가기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("서점 찾기");
        actionBar.setDisplayHomeAsUpEnabled(true);

//        바텀 네비게이션 이동
        bottomNavi = findViewById(R.id.bottom_navigation);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });


        ibtnYk = findViewById(R.id.ibtnYk);

        ibtnYk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), H_Map_Detail1.class);
                startActivity(intent);
            }
        });

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