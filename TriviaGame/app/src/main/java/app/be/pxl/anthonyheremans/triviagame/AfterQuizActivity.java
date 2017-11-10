package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import app.be.pxl.anthonyheremans.triviagame.DomainClasses.BufferClassRoundTwo;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.User;

import static app.be.pxl.anthonyheremans.triviagame.MainActivity.USER_PREFS;
import static app.be.pxl.anthonyheremans.triviagame.NewGameActivity.API_PREFS;
import static app.be.pxl.anthonyheremans.triviagame.RoundOneActivity.SCORE_PREFS;

public class AfterQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_quiz);

        String score = getIntent().getStringExtra("SCORE_OF_USER");

        TextView labelscore = (TextView) findViewById(R.id.lblScore);
        labelscore.setText(score);

        TextView labelcoins = (TextView) findViewById(R.id.lblCoins);
        int coins =  Integer.parseInt(score)/10;
        labelcoins.setText(String.valueOf(coins));


    }
    public void sendTextClick(View view) {
        final EditText numberEt = (EditText) findViewById(R.id.txtPhonenumber);
        final EditText messageEt = (EditText) findViewById(R.id.txtMessage);

        String number = numberEt.getText().toString();
        String message = messageEt.getText().toString();

        if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(number)) {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));
            smsIntent.putExtra("sms_body", message);
            startActivity(smsIntent);
        }
    }
    public void noSendTextClick(View view) {
        Intent home = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(home);
    }

}
