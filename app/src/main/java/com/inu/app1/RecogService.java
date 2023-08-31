package com.inu.app1;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import com.inu.app1.Keyword.Keyword;
import com.inu.app1.Keyword.KeywordDatabase;

public class RecogService extends Service {
    SpeechRecognizer mRecognizer;
    Intent i;
    KeywordDatabase database;

    public RecogService() {
    }

    RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Toast.makeText(getApplicationContext(),"xptmxm",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {
           Keyword keyword = chkSpeech(mRecognizer.RESULTS_RECOGNITION);
           if(keyword==null) {
               mRecognizer.stopListening();
               mRecognizer.destroy();
               startRecognizer();
           }
           else{
               onRecogedKeyword(keyword);
               mRecognizer.stopListening();
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
        Toast.makeText(this,"키워드 감지 : "+keyword.keyword+"\n중요도 :"+keyword.important,Toast.LENGTH_LONG);
    }
public void startRecognizer() {
    mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
    mRecognizer.setRecognitionListener(listener);
    mRecognizer.startListening(i);
}
public Keyword chkSpeech(String speech){
   return database.keywordDAO().findKeyword(speech).get(0);
}
    @Override
    public IBinder onBind(Intent intent) {
    database = KeywordDatabase.getDatabase(this);
    Log.i("xptmxm","xptmxm");
        // TODO: Return the communication channel to the service.
       i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
       i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
       i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
       startRecognizer();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}