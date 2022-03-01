package com.bitc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class L_LoginActivity extends AppCompatActivity {

    private EditText et_log_id, et_log_pass;
    private Button btn_login;
    private TextView register;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_login);

        //        액션바 뒤로가기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("로그인");
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

        et_log_id = findViewById(R.id.et_log_id);
        et_log_pass = findViewById(R.id.et_log_pass);
        btn_login = findViewById(R.id.btn_login);
        register = findViewById(R.id.register);

        //회원가입을 클릭시 수행
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(L_LoginActivity.this, L_RegisterActivity.class);
                startActivity(intent);
            }
        });

        //로그인 버튼을 클릭시 수행
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_log_id.getText().toString();
                String userPass = et_log_pass.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { //로그인 성공일 경우
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                String userPhone = jsonObject.getString("userPhone");
                                String userMail = jsonObject.getString("userMail");

                                //sharedpreferences 선언
                                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                                editor = pref.edit();
                                editor.putString("userID", userID);
                                editor.putString("userPassword", userPass);
                                editor.putString("userName", userName);
                                editor.putString("userPhone", userPhone);
                                editor.putString("userMail", userMail);
                                editor.apply(); // 저장

                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(L_LoginActivity.this, k_Main.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPass);

                                startActivity(intent);
                            } else { //로그인 실패할 경우
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                L_LoginRequest loginRequest = new L_LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(L_LoginActivity.this);
                queue.add(loginRequest);
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
