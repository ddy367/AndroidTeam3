package com.bitc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class H_BoardWrite extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    EditText edtTitle;
    EditText edtContents;
    EditText edtId;
    Button btnup;

    String id;

    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_write);
        setTitle("자유 게세판");

        edtTitle = findViewById(R.id.edtTitle);
        edtContents = findViewById(R.id.edtContents);
        edtId = findViewById(R.id.edtId);
        btnup = findViewById(R.id.btnUp);

        btnup.setOnClickListener(this);

        //        바텀 네비게이션 이동
        bottomNavi = findViewById(R.id.bottom_navigation);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        id = fStore.collection("board").document().getId();
        Map<String, Object> post = new HashMap<>();
        post.put("id", id);
        post.put("title", edtTitle.getText().toString());
        post.put("contents", edtContents.getText().toString());
        post.put("name", edtId.getText().toString());

        fStore.collection("board").document(id).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(H_BoardWrite.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(H_BoardWrite.this, "업로드 실패", Toast.LENGTH_SHORT).show();;
                    }
                });
    }
    /*액션 바 이동*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.k_bottom_navigation, menu);
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        String id = pref.getString("userID","1");

        if(id !="1") {
            if(id.equals("admin")) {
                menu.add(menu.NONE, Menu.FIRST+10, menu.NONE, "회원리스트");
            } else{

            }
            menu.add(menu.NONE, Menu.FIRST, menu.NONE, "로그아웃");
        }
        else {

        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        String id = pref.getString("userID","1");


        switch (item.getItemId()) {
            case R.id.tab1:
                if(id != "1") {
                    Intent intent1 = new Intent(getApplicationContext(), L_MemberDetailActivity.class);
                    startActivity(intent1);
                    break;
                }
                else {
                    Intent intent6 = new Intent(getApplicationContext(), L_LoginActivity.class);
                    startActivity(intent6);
                    break;
                }
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
                Intent intent5 = new Intent(getApplicationContext(), H_Board.class);
                startActivity(intent5);
                break;
            case Menu.FIRST+10:
                new L_BackgroundTask().execute();
                break;
            case Menu.FIRST:
                editor.clear();
                editor.commit();
                Intent intent7 = new Intent(getApplicationContext(), k_Main.class);
                startActivity(intent7);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    /*바텀 네비게이션 이동*/
    private void BottomNavigate(int item) {

        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        String id = pref.getString("userID","1");
        switch (item)
        {
            case R.id.tab1:
                if(id != "1") {
                    Intent intent1 = new Intent(getApplicationContext(), L_MemberDetailActivity.class);
                    startActivity(intent1);
                    break;
                }
                else {
                    Intent intent6 = new Intent(getApplicationContext(), L_LoginActivity.class);
                    startActivity(intent6);
                    break;
                }
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
                Intent intent5 = new Intent(getApplicationContext(), H_Board.class);
                startActivity(intent5);
                break;


        }
    }
}