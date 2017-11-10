package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import app.be.pxl.anthonyheremans.triviagame.DomainClasses.BufferClassRoundOne;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.RoundOne;
import app.be.pxl.anthonyheremans.triviagame.Logic.Drawer;
import app.be.pxl.anthonyheremans.triviagame.Service.ApiSerivce;

import static app.be.pxl.anthonyheremans.triviagame.NewGameActivity.API_PREFS;
import static java.net.Proxy.Type.HTTP;

public class RoundOneActivity extends AppCompatActivity {
    //variables
    private int score = 0;
    private String categoryValue;
    private String radioButtonValue;
    ApiSerivce api = new ApiSerivce();
    private RoundOne[] roundOne;
    int questionId = 0;

    // variables for shared preferences api
    private SharedPreferences sharePrefs;
    private SharedPreferences.Editor editor;
    private Context context;

    //shared preference for Score
    private SharedPreferences sharePrefsScore;
    public static final String SCORE_PREFS = "scorePrefs";
    private SharedPreferences.Editor editorScore;
    private Context contextScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_one);

        //setting score prefs
        contextScore = getApplicationContext();
        //        //nav list
        ListView list = (ListView) findViewById(R.id.navList);
        Drawer drawer = new Drawer();
        drawer.addDrawerItems(getApplicationContext(), list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent t;
                switch (position) {

                    case 0:
                        Toast.makeText(getApplicationContext(), "Home",
                                Toast.LENGTH_SHORT).show();
                        t = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(t);

                        break;
                    case 1:

                        Toast.makeText(getApplicationContext(), "New Game",
                                Toast.LENGTH_SHORT).show();
                        t = new Intent(getApplicationContext(), NewGameActivity.class);
                        startActivity(t);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Go to shop",
                                Toast.LENGTH_SHORT).show();
                        t = new Intent(getApplicationContext(), ShopActivity.class);
                        startActivity(t);
                        break;
                    case 3:

                        Toast.makeText(getApplicationContext(), "Logout",
                                Toast.LENGTH_SHORT).show();
                        //delete share pref
                        SharedPreferences settings = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        t = new Intent(getApplicationContext(), ShopActivity.class);
                        startActivity(t);
                        break;
                }
            }
        });

        // getting values from shared preferences
        context = getApplicationContext();
        BufferClassRoundOne buffer = new BufferClassRoundOne();
        sharePrefs = context.getSharedPreferences(API_PREFS, context.MODE_PRIVATE);
        editor = sharePrefs.edit();
        String difficulty = sharePrefs.getString("DIFFICULTY", "easy");
        String category = sharePrefs.getString("CATEGORY", "9");
        String type = "boolean";
        String value = "";
        // put variables in api , and get api string
        String[] myTaskParms = {difficulty, category, type};

        //Json/Gson parser
        try {
            String jsonString = api.execute(myTaskParms).get();


            //converter for utf8
            byte ptext[] = new byte[0];
            try {
                ptext = jsonString.getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                value = new String(ptext, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
           


            Gson gson = new GsonBuilder().create();
            buffer = gson.fromJson( value, BufferClassRoundOne.class);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //set all questions
        roundOne = buffer.getResults();
        setQuestion(questionId);
    }

    //TODO :: 1 event handler
    //click on right button
    public void rightClick(View view) {
        if (roundOne[questionId].getCorrect_answer().equals("True")) {
            Toast.makeText(getApplicationContext(), "Juiste Antwoord",
                    Toast.LENGTH_SHORT).show();
            score += 5;
        } else {
            Toast.makeText(getApplicationContext(), "Foute Antwoord",
                    Toast.LENGTH_SHORT).show();
        }
        questionId++;
        setQuestion(questionId);
    }

    //click on wrong button
    public void wrongClick(View view) {
        if (roundOne[questionId].getCorrect_answer().equals("False")) {
            Toast.makeText(getApplicationContext(), "Juiste Antwoord",
                    Toast.LENGTH_SHORT).show();
            score += 5;
        } else {
            Toast.makeText(getApplicationContext(), "Foute Antwoord",
                    Toast.LENGTH_SHORT).show();
        }
        questionId++;
        setQuestion(questionId);
    }

    //set question if last question go to next view
    public void setQuestion(int questionId) {

        if (questionId < 10) {
            TextView questionNumber = (TextView) findViewById(R.id.QuestionNumber);
            questionNumber.setText("Question " + (questionId + 1));
            TextView question = (TextView) findViewById(R.id.txtQuestion);
            question.setText(roundOne[questionId].getQuestion());
            // set coins
            TextView coinsTextView = (TextView)findViewById(R.id.TxtCoins);

            //better approuch than .tostring()
            coinsTextView.setText(String.valueOf(score));
        } else {
            //make new preferences for score
            sharePrefsScore = contextScore.getSharedPreferences(SCORE_PREFS, contextScore.MODE_PRIVATE);
            editorScore = sharePrefsScore.edit();

            // Score to pass it trough shared prefs
            editor.putString("SCORE", String.valueOf(score) );
            editor.commit();

            //go to new Activity
            Intent roudnTwoActivty = new Intent(getApplicationContext(), RoundTwoActivity.class);
            startActivity(roudnTwoActivty);

        }

    }
}
