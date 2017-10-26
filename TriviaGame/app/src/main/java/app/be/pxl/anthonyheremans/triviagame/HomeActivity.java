package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import app.be.pxl.anthonyheremans.triviagame.DomainClasses.BufferClassRoundOne;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.User;

import static app.be.pxl.anthonyheremans.triviagame.MainActivity.USER_PREFS;
import static app.be.pxl.anthonyheremans.triviagame.NewGameActivity.API_PREFS;

public class HomeActivity extends AppCompatActivity {

    // variables for shared preferences
    private SharedPreferences sharePrefs;

    private SharedPreferences.Editor editor;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // getting values from shared preferences
        context = getApplicationContext();
        sharePrefs = context.getSharedPreferences(USER_PREFS, context.MODE_PRIVATE);
        editor = sharePrefs.edit();

        //retrieve logde in user
        Gson gson = new Gson();
        String json = sharePrefs.getString("USEROBJECT", "");
        User user = gson.fromJson(json, User.class);

        // set coins
        TextView coinsTextView = (TextView)findViewById(R.id.TxtCoins);

        //better approuch than .tostring()
        coinsTextView.setText(String.valueOf(user.getCOINS()));


    }

    //go to next activity
    public void newGameClick(View view)
    {
        Intent startNewGameActivity = new Intent(getApplicationContext(),NewGameActivity.class);
        startActivity(startNewGameActivity);
    }
}
