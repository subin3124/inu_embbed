package com.inu.app1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.inu.app1.Keyword.Keyword;
import com.inu.app1.Keyword.KeywordDatabase;

import java.util.ArrayList;
import java.util.List;

public class KeywordListActivity extends AppCompatActivity {

    ListView ltv;
    KeywordDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyword_list);
        ltv = findViewById(R.id.ltv);

        List<String> arrayList = new ArrayList<>();
        database = KeywordDatabase.getDatabase(this);
        for(Keyword keyword : database.keywordDAO().getAllKeywords()) {
            arrayList.add(keyword.id + ", 키워드 : " + keyword.keyword + "\n중요도 : " + keyword.important);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.simple_list_item,R.id.arydtv,arrayList);
        ltv.setAdapter(adapter);
        ltv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = ltv.getItemAtPosition(i).toString();
                String id =  str.split(",")[0];
                new AlertDialog.Builder(getApplicationContext()).setTitle("삭제").setMessage("키워드를 삭제할까요?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.keywordDAO().deleteKeyword(Long.parseLong(id));
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
                return false;
            }
        });
    }
}