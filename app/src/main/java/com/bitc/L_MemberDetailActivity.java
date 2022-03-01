package com.bitc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class L_MemberDetailActivity extends AppCompatActivity {

    private EditText et_m_id, et_m_pass, et_m_name, et_m_phone, et_m_email;
    private Button btn_with, btn_Update;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_member_detail);

        //        액션바 뒤로가기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("회원정보");
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

        et_m_id = findViewById(R.id.et_m_id);
        et_m_pass = findViewById(R.id.et_m_pass);
        et_m_name = findViewById(R.id.et_m_name);
        et_m_phone = findViewById(R.id.et_m_phone);
        et_m_email = findViewById(R.id.et_m_email);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        String id = pref.getString("userID", "1");
        et_m_id.setText(id);
        String pass = pref.getString("userPassword", "1");
        et_m_pass.setText(pass);
        String name = pref.getString("userName", "1");
        et_m_name.setText(name);
        String phone = pref.getString("userPhone", "1");
        et_m_phone.setText(phone);
        String mail = pref.getString("userMail", "1");
        et_m_email.setText(mail);

        btn_with = findViewById(R.id.btn_with);
        btn_with.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //4. 콜백 처리부분(volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if(success){
                                editor.clear();
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "회원탈퇴에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(L_MemberDetailActivity.this, L_LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                //volley 사용법
                //1. RequestObject를 생성한다. 이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
                //위에서 userID를 final로 선언해서 아래 처럼 가능함
                L_DeleteRequest deleteRequest = new L_DeleteRequest(id, responseListener);
                //2. RequestQueue를 생성한다.
                //여기서 UserListAdapter는 Activity에서 상속받은 클래스가 아니므로 Activity값을 생성자로 받아서 사용한다
                RequestQueue queue = Volley.newRequestQueue(L_MemberDetailActivity.this);
                queue.add(deleteRequest);
            }//onclick
        });

        btn_Update = findViewById(R.id.btn_update);
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = et_m_id.getText().toString();
                String userPass = et_m_pass.getText().toString();
                String userName = et_m_name.getText().toString();
                String userPhone = et_m_phone.getText().toString();
                String userEmail = et_m_email.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원수정에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(L_MemberDetailActivity.this, L_LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                L_UpdateRequest updateRequest = new L_UpdateRequest(userId, userPass, userName, userPhone, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(L_MemberDetailActivity.this);
                queue.add(updateRequest);

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

    class L_BackgroundTask extends AsyncTask<Void, Void, String> {
        //모든회원에 대한 정보를 가져오기 위한 쓰레드

        String target;

        //    private Activity parentActivity;

//    public Context getContext() {
//        return context;
//    }

//    public void setContext(Context context) {
//        this.context = context;
//    }

//        private Context mContext;


        @Override
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://haun2.ivyro.net/List.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url = new URL(target);//URL 객체 생성

//                mContext = MyApp.ApplicationContext();

                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //바이트단위 입력스트림 생성 소스는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder클래스를 사용함
                StringBuilder stringBuilder = new StringBuilder();

                //한줄씩 읽어서 stringBuilder에 저장함
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(L_MemberDetailActivity.this, L_MemberListActivity.class); //parentActivity값이 널이라서 현재 에러가 나고 있습니다
            intent.putExtra("userList", result);//파싱한 값을 넘겨줌
            L_MemberDetailActivity.this.startActivity(intent);//ManagementActivity로 넘어감
        }

    }
}