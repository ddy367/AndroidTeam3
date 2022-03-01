package com.bitc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_board);
        setTitle("자유 게시판");

        rView = findViewById(R.id.rView);

        //취소버튼 (admin일경우 나타남)
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        String id = pref.getString("userID","1");

        btn = getLayoutInflater().inflate(R.layout.active_h_board_item, null, false);

        btn_delete = btn.findViewById(R.id.btn_delete);
        if (!id.equals("admin")) {
            btn_delete.setVisibility(View.GONE);
        } else{
            btn_delete.setVisibility(View.VISIBLE);
        }

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

        HBoardList = new ArrayList<>();

        HBoardList = new ArrayList<>();

        fStore.collection("board").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    String id = (String) dc.getDocument().getData().get("id");
                    String title = (String) dc.getDocument().get("title");
                    String contents = (String) dc.getDocument().get("contents");
                    String name = (String) dc.getDocument().get("name");
                    H_BoardItem data = new H_BoardItem(id, title, contents, name);

                    HBoardList.add(data);
                }
                mAdapter = new MainAdapter(HBoardList);
                rView.setAdapter(mAdapter);
            }
        });
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

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            H_BoardItem data = HBoardList.get(position);
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

            public MainViewHolder(View itemView) {
                super(itemView);

                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvName = itemView.findViewById(R.id.tvName);
                tvContents = itemView.findViewById(R.id.tvContents);
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
}