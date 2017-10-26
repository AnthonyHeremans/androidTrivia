package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import app.be.pxl.anthonyheremans.triviagame.DomainClasses.BufferClassRoundOne;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.RoundOne;
import app.be.pxl.anthonyheremans.triviagame.Service.ApiSerivce;

import static app.be.pxl.anthonyheremans.triviagame.NewGameActivity.API_PREFS;

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
    private static final String SCORE_PREFS = "scorePrefs";
    private SharedPreferences.Editor editorScore;
    private Context contextScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_one);

        //setting score prefs
        contextScore = getApplicationContext();

        // getting values from shared preferences
        context = getApplicationContext();
        BufferClassRoundOne buffer = new BufferClassRoundOne();
        sharePrefs = context.getSharedPreferences(API_PREFS, context.MODE_PRIVATE);
        editor = sharePrefs.edit();
        String difficulty = sharePrefs.getString("DIFFICULTY", "easy");
        String category = sharePrefs.getString("CATEGORY", "9");
        String type = "boolean";

        // put variables in api , and get api string
        String[] myTaskParms = {difficulty, category, type};

        //Json/Gson parser
        try {
            String jsonString = api.execute(myTaskParms).get();

            Gson gson = new GsonBuilder().create();
            buffer = gson.fromJson(jsonString, BufferClassRoundOne.class);

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
