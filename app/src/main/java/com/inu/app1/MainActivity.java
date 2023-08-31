package com.inu.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.app1.Keyword.Keyword;
import com.inu.app1.Keyword.KeywordDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SpeechRecognizer mRecognizer;
    TextView tv1;
    Intent i;
    Vibrator vibrator;
    TextView logtv;
    KeywordDatabase database;
    Button plusKeywordBtn;
    Button listKeywordBtn;
    RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
           // logtv.setText("테스트1");
           // Toast.makeText(getApplicationContext(), "xptmxm", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
         //  logtv.setText("테스트2");
        //    Toast.makeText(getApplicationContext(), "xptmxm2", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
          //  logtv.setText("테스트3");
          //  Toast.makeText(getApplicationContext(), "xptmxm3", Toast.LENGTH_SHORT).show();
         //   onResults(new Bundle());
            //  mRecognizer.destroy();
        }

        @Override
        public void onError(int i) {
         //  logtv.setText("테스트4"+i);
            // Toast.makeText(getApplicationContext(), "xptmxm4"+i, Toast.LENGTH_SHORT).show();
            mRecognizer.stopListening();
            //  mRecognizer.destroy();
            mRecognizer.destroy();
            startRecognizer();
        }

        @Override
        public void onResults(Bundle bundle) {
            try {
                if(bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!=null) {
                    Keyword keyword;
                    keyword = new Keyword();
                    List<String> ary = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    logtv.setText(ary.get(0));
                    keyword = chkSpeech(ary.get(0));
                    switch(keyword.important){
                        case 5: tv1.setBackgroundColor(Color.RED);tv1.setText("5");vibrator.vibrate(1000);break;
                        case 4: tv1.setBackgroundColor(Color.rgb(255,206,85));vibrator.vibrate(800);tv1.setText("4");break;
                        case 3: tv1.setBackgroundColor(Color.YELLOW);tv1.setText("3");vibrator.vibrate(800);break;
                        case 2: tv1.setBackgroundColor(Color.GRAY);tv1.setText("2");vibrator.vibrate(800);break;
                        case 1: tv1.setBackgroundColor(Color.DKGRAY);tv1.setText("1");;vibrator.vibrate(600);break;
                    }
                //    Toast.makeText(getApplicationContext(), ary.get(0), Toast.LENGTH_SHORT).show();
                    if (keyword == null) {
                    } else {
                        onRecogedKeyword(keyword);
                    }
                }
                mRecognizer.stopListening();
                //  mRecognizer.destroy();
                mRecognizer.destroy();
                 startRecognizer();
            }catch (Exception e){
                mRecognizer.stopListening();
                //  mRecognizer.destroy();
                mRecognizer.destroy();
                startRecognizer();
                }
        }
        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
    public void onRecogedKeyword(Keyword keyword) {
        Toast.makeText(this,"키워드 감지 : "+keyword.keyword+"\n중요도 :"+keyword.important,Toast.LENGTH_LONG).show();
    }
    public void startRecognizer() {
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(i);
    }
    public Keyword chkSpeech(String speech){
        try{
            Log.d("aa",speech);
            Keyword k;
           if( database.keywordDAO().findKeyword(speech)!=null)
              k = database.keywordDAO().findKeyword(speech).get(0);
            else
                k = new Keyword();
            return k;
        }catch (Exception e){
         //  System.out.println(e.getMessage());
          //  tv1.setText(e.getLocalizedMessage());
            throw e;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plusKeywordBtn = findViewById(R.id.plusKeyword);
        logtv = findViewById(R.id.logtv);
        tv1 = findViewById(R.id.tv1);
        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        listKeywordBtn = findViewById(R.id.listKeyword);
        database = KeywordDatabase.getDatabase(this);
        Log.i("xptmxm","xptmxm");
        // TODO: Return the communication channel to the service.

        startRecognizer();

   //     startService(new Intent(this,RecogService.class));
        Log.i("aaa","aaa");
        plusKeywordBtn.setOnClickListener((view) -> {
            startActivity(new Intent(getApplicationContext(),AddKeywordActivity.class));
        });
        listKeywordBtn.setOnClickListener((view) -> {
            startActivity(new Intent(getApplicationContext(),KeywordListActivity.class));
        });
    }
}