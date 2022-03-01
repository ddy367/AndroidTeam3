package com.bitc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class H_Board extends AppCompatActivity {

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    RecyclerView rView;
    FloatingActionButton fabWrite;
    Button btn_delete;
    MainAdapter mAdapter;
    List<H_BoardItem> HBoardList;
    private View btn;

    BottomNavigationView bottomNavi;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_board);
        setTitle("자유 게시판");

        rView = findViewById(R.id.rView);

        //취소버튼 (admin일경우 나타남)
//        SharedPreferences pref;
//        SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
//        String id = pref.getString("userID","1");

//        btn = getLayoutInflater().inflate(R.layout.active_h_board_item, null, false);
//
//        btn_delete = btn.findViewById(R.id.btn_delete);
//        if (!id.equals("admin")) {
//            btn_delete.setVisibility(View.GONE);
//        } else{
//            btn_delete.setVisibility(View.VISIBLE);
//        }

        fabWrite = findViewById(R.id.fabWrite);
        fabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), H_BoardWrite.class);
                startActivity(intent);
            }
        });

        //        바텀 네비게이션 이동
        bottomNavi = findViewById(R.id.bottom_navigation);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });

//        HBoardList = new ArrayList<>();

        HBoardList = new ArrayList<>();

        fStore.collection("board").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                String userInfo = pref.getString("userID","1");

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    String id = (String) dc.getDocument().getData().get("id");
                    String title = (String) dc.getDocument().get("title");
                    String contents = (String) dc.getDocument().get("contents");
                    String name = (String) dc.getDocument().get("name");

                    H_BoardItem data;
                    if (userInfo.equals("admin")) {
                        data = new H_BoardItem(id, title, contents, name, View.VISIBLE);
                    }
                    else {
                        data = new H_BoardItem(id, title, contents, name, View.INVISIBLE);
                    }


                    HBoardList.add(data);
                }
                mAdapter = new MainAdapter(HBoardList);
                rView.setAdapter(mAdapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        String id = pref.getString("userID","1");

//        btn = getLayoutInflater().inflate(R.layout.active_h_board_item, null, false);

//        btn_delete = btn.findViewById(R.id.btn_delete);
//        btn_delete = btn.
//        if (!id.equals("admin")) {
//            btn_delete.setVisibility(View.INVISIBLE);
//        } else{
//            btn_delete.setVisibility(View.VISIBLE);
//        }
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

        private List<H_BoardItem> HBoardList;

        public MainAdapter(List<H_BoardItem> HBoardList) {
            this.HBoardList = HBoardList;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.active_h_board_item, parent, false));
        }

        @SuppressLint("WrongConstant")
        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            H_BoardItem data = HBoardList.get(position);
            holder.btn_delete.setVisibility(data.getVisible());
            holder.tvTitle.setText(data.getTitle());
            holder.tvName.setText(data.getName());
            holder.tvContents.setText(data.getContents());

        }

        @Override
        public int getItemCount() {
            return HBoardList.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            TextView tvName;
            TextView tvContents;
            Button btn_delete;

            public MainViewHolder(View itemView) {
                super(itemView);

                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvName = itemView.findViewById(R.id.tvName);
                tvContents = itemView.findViewById(R.id.tvContents);
                btn_delete = itemView.findViewById(R.id.btn_delete);
            }
        }
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
            Intent intent = new Intent(H_Board.this, L_MemberListActivity.class); //parentActivity값이 널이라서 현재 에러가 나고 있습니다
            intent.putExtra("userList", result);//파싱한 값을 넘겨줌
            H_Board.this.startActivity(intent);//ManagementActivity로 넘어감
        }

    }
}