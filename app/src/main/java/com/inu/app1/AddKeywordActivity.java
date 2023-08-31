package com.inu.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.inu.app1.Keyword.Keyword;
import com.inu.app1.Keyword.KeywordDatabase;

public class AddKeywordActivity extends AppCompatActivity {

    EditText edKeyword;
    EditText edImport;
    Button btnAddKeyword;
    KeywordDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_keyword);
        edKeyword = findViewById(R.id.keykword);
        edImport = findViewById(R.id.importance);
        btnAddKeyword = findViewById(R.id.addKeywordBtn);
        db = KeywordDatabase.getDatabase(this);
        btnAddKeyword.setOnClickListener((view) -> {
            Keyword keyword = new Keyword();
            keyword.keyword = edKeyword.getText().toString();
            keyword.important = Integer.parseInt(edImport.getText().toString());
        db.keywordDAO().addKeyword(keyword);
        finish();
        });
    }
}