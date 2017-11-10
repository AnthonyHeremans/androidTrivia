package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import app.be.pxl.anthonyheremans.triviagame.DataBaseHelper.DataBaseHelperClass;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.BufferClassRoundOne;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.User;
import app.be.pxl.anthonyheremans.triviagame.Logic.Drawer;

import static android.R.id.list;
import static app.be.pxl.anthonyheremans.triviagame.MainActivity.USER_PREFS;
import static app.be.pxl.anthonyheremans.triviagame.NewGameActivity.API_PREFS;

public class HomeActivity extends AppCompatActivity {
    // declare database helper
    DataBaseHelperClass myDb;
    // variables for shared preferences
    private SharedPreferences sharePrefs;

    private SharedPreferences.Editor editor;
    private Context context;

    User user;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = getApplicationContext();
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
                        //  t = new Intent(getApplicationContext(), HomeActivity.class);
                        //startActivity(t);
                        Toast.makeText(getApplicationContext(), "Home",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // t = new Intent(getApplicationContext(), NewGameActivity.class);
                        //startActivity(t);
                        Toast.makeText(getApplicationContext(), "New Game",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Go to shop",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Logout",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
//will create the database
        myDb = new DataBaseHelperClass(this);

        // getting values from shared preferences
        //context = getApplicationContext();
        sharePrefs = context.getSharedPreferences(USER_PREFS, context.MODE_PRIVATE);
        editor = sharePrefs.edit();

        //retrieve logde in user
        Gson gson = new Gson();
        String json = sharePrefs.getString("USEROBJECT", "");
        user = gson.fromJson(json, User.class);

        // set coins
        TextView coinsTextView = (TextView) findViewById(R.id.TxtCoins);

        //better approuch than .tostring()
        coinsTextView.setText(String.valueOf(user.getCOINS()));

        //fill listview with played games
        //Declaration part

        setListView("easy");


    }

    //go to next activity
    public void newGameClick(View view) {
        Intent startNewGameActivity = new Intent(getApplicationContext(), NewGameActivity.class);
        startActivity(startNewGameActivity);
    }

    public void leftClick(View view) {
        TextView difficulty = (TextView) findViewById(R.id.TxtDifficulty);
        String difficultyText = difficulty.getText().toString();
        if (!difficultyText.equals("Easy")) {
            if (difficultyText.equals("Medium")) {
                lv.setAdapter(null);
                difficulty.setText("Easy");
                setListView("easy");

            } else if (difficultyText.equals("Hard")) {
                lv.setAdapter(null);
                difficulty.setText("Medium");
                setListView("medium");
            }
        }


    }

    public void rightBtnClick(View view) {
        TextView difficulty = (TextView) findViewById(R.id.TxtDifficulty);
        String difficultyText = difficulty.getText().toString();
        if (!difficultyText.equals("Hard")) {
            if (difficultyText.equals("Medium")) {
                lv.setAdapter(null);
                difficulty.setText("Hard");
                setListView("hard");
            } else if (difficultyText.equals("Easy")) {
                lv.setAdapter(null);
                difficulty.setText("Medium");
                setListView("medium");
            }
        }


    }

    public void setListView(String difficulty) {

        listItems = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.LstScores);
        //TODO :: dificulty not hard coded
        //get list data
        Cursor result = myDb.getPlayedGames(user.getUSERID(), difficulty);
        while (result.moveToNext()) {

            listItems.add(result.getString(3) + " : " + result.getString(4));
        }
        //arraylist Append
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        lv.setAdapter(adapter);
    }

}
