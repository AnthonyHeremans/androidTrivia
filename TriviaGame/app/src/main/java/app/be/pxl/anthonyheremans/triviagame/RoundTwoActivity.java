package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import app.be.pxl.anthonyheremans.triviagame.DataBaseHelper.DataBaseHelperClass;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.BufferClassRoundTwo;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.RoundTwo;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.User;
import app.be.pxl.anthonyheremans.triviagame.Logic.SubjectHelper;
import app.be.pxl.anthonyheremans.triviagame.Service.ApiSerivce;

import static app.be.pxl.anthonyheremans.triviagame.MainActivity.USER_PREFS;
import static app.be.pxl.anthonyheremans.triviagame.NewGameActivity.API_PREFS;
import static app.be.pxl.anthonyheremans.triviagame.RoundOneActivity.SCORE_PREFS;

public class RoundTwoActivity extends AppCompatActivity {
    //values
    private String categoryValue;
    private String radioButtonValue;
    ApiSerivce api = new ApiSerivce();
    private RoundTwo[] roundTwo;
    int questionId = 0;
    String difficulty;
    String category;
    String type;
    DataBaseHelperClass myDb;


    //SharedPrefs for user
    private SharedPreferences sharePrefsUser;
    private SharedPreferences.Editor editorUser;
    private Context contextUser;
    User user;

    //SharedPrefs for api
    private SharedPreferences sharePrefs;
    private SharedPreferences.Editor editor;
    private Context context;

    //sharedPrefs for score
    private SharedPreferences sharePrefsScore;
    private SharedPreferences.Editor editorScore;
    private Context contextScore;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_two);
        //setup database
        myDb = new DataBaseHelperClass(this);
        //get data from preferences for api
        context = getApplicationContext();
        BufferClassRoundTwo buffer = new BufferClassRoundTwo();
        sharePrefs = context.getSharedPreferences(API_PREFS, context.MODE_PRIVATE);
        editor = sharePrefs.edit();
        difficulty = sharePrefs.getString("DIFFICULTY", "easy");
        category = sharePrefs.getString("CATEGORY", "9");
        type = "multiple";

        //shared prefs for score
        contextScore = getApplicationContext();
        sharePrefsScore = contextScore.getSharedPreferences(SCORE_PREFS, contextScore.MODE_PRIVATE);
        editorScore = sharePrefsScore.edit();
        score = Integer.parseInt(sharePrefs.getString("SCORE", ""));

        // shared prefs for user
        contextUser = getApplicationContext();
        sharePrefsUser = contextUser.getSharedPreferences(USER_PREFS, contextUser.MODE_PRIVATE);
        editorUser = sharePrefsUser.edit();

        //retrieve logde in user
        Gson gsonUser = new Gson();
        String json = sharePrefsUser.getString("USEROBJECT", "");
        user = gsonUser.fromJson(json, User.class);

        //put values in api
        String[] myTaskParms = {difficulty, category, type};

        //api call
        try {
            String jsonString = api.execute(myTaskParms).get();
            Gson gson = new GsonBuilder().create();
            buffer = gson.fromJson(jsonString, BufferClassRoundTwo.class);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        roundTwo = buffer.getResults();
        setQuestion(questionId);
    }

    // set question and set buttons
    public void setQuestion(int questionId) {
        if (questionId < 10) {
            TextView questionNumber = (TextView) findViewById(R.id.questionNumber);
            questionNumber.setText("Question " + (questionId + 1));
            TextView question = (TextView) findViewById(R.id.txtQuestion);
            question.setText(roundTwo[questionId].getQuestion());

            //make 1 array from answers
            String[] answersArray = new String[4];
            answersArray[0] = roundTwo[questionId].getCorrect_answer();
            for (int i = 1; i < answersArray.length; i++) {
                answersArray[i] = roundTwo[questionId].getIncorrect_answers()[i - 1];

            }
            //shuffle array random
            String[] mixedAnswers = shuffleArray(answersArray);

            //set button data
            Button btnOne = (Button) findViewById(R.id.btnAnswerOne);
            Button btnTwo = (Button) findViewById(R.id.btnAnswerTwo);
            Button btnThree = (Button) findViewById(R.id.btnAnswerThree);
            Button btnFour = (Button) findViewById(R.id.btnAnswerfour);

            btnOne.setText(mixedAnswers[0]);
            btnTwo.setText(mixedAnswers[1]);
            btnThree.setText(mixedAnswers[2]);
            btnFour.setText(mixedAnswers[3]);
            // set coins
            TextView coinsTextView = (TextView) findViewById(R.id.TxtCoins);

            //better approuch than .tostring()
            coinsTextView.setText(String.valueOf(score));
        } else {
            String subject = SubjectHelper.subjectIdToString(category);
            boolean isInserted = myDb.insertScore(score, difficulty, subject, user.getUSERID());
            if (isInserted) {
                Toast.makeText(getApplicationContext(), String.valueOf(score),
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Score isn't saved",
                        Toast.LENGTH_SHORT).show();
            }

            Intent after = new Intent(getApplicationContext(), AfterQuizActivity.class);
            after.putExtra("SCORE_OF_USER",String.valueOf(score));
            startActivity(after);
//            //TODO : go to thirth screen NOT SECOND
//            Intent roudnTwoActivty = new Intent(getApplicationContext(),RoundTwoActivity.class);
//            startActivity(roudnTwoActivty);


        }

    }

    // Implementing Fisher–Yates shuffle
    private static String[] shuffleArray(String[] array) {
        int index;
        String temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    //TODO :: misschien 1 event handler van maken ?
    public void answerOneClick(View view) {
        Button btnOne = (Button) findViewById(R.id.btnAnswerOne);
        if (roundTwo[questionId].getCorrect_answer().equals(btnOne.getText())) {
            Toast.makeText(getApplicationContext(), "Juiste Antwoord",
                    Toast.LENGTH_SHORT).show();
            score += 7;
        } else {
            Toast.makeText(getApplicationContext(), "Foute Antwoord",
                    Toast.LENGTH_SHORT).show();
        }
        questionId++;
        setQuestion(questionId);

    }

    public void answerTwoClick(View view) {
        Button btnTwo = (Button) findViewById(R.id.btnAnswerTwo);
        if (roundTwo[questionId].getCorrect_answer().equals(btnTwo.getText())) {
            Toast.makeText(getApplicationContext(), "Juiste Antwoord",
                    Toast.LENGTH_SHORT).show();
            score += 7;
        } else {
            Toast.makeText(getApplicationContext(), "Foute Antwoord",
                    Toast.LENGTH_SHORT).show();
        }
        questionId++;
        setQuestion(questionId);

    }

    public void answerThreeClick(View view) {
        Button btnThree = (Button) findViewById(R.id.btnAnswerThree);
        if (roundTwo[questionId].getCorrect_answer().equals(btnThree.getText())) {
            Toast.makeText(getApplicationContext(), "Juiste Antwoord",
                    Toast.LENGTH_SHORT).show();
            score += 7;
        } else {
            Toast.makeText(getApplicationContext(), "Foute Antwoord",
                    Toast.LENGTH_SHORT).show();
        }
        questionId++;
        setQuestion(questionId);

    }

    public void answerFourClick(View view) {
        Button btnFour = (Button) findViewById(R.id.btnAnswerfour);
        if (roundTwo[questionId].getCorrect_answer().equals(btnFour.getText())) {
            Toast.makeText(getApplicationContext(), "Juiste Antwoord",
                    Toast.LENGTH_SHORT).show();
            score += 7;
        } else {
            Toast.makeText(getApplicationContext(), "Foute Antwoord",
                    Toast.LENGTH_SHORT).show();
        }
        questionId++;
        setQuestion(questionId);

    }
}
