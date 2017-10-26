package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.be.pxl.anthonyheremans.triviagame.Logic.SubjectHelper;

public class NewGameActivity extends AppCompatActivity {
    private String categoryValue;
    private String radioButtonValue;

    //shared preference for api call
    private SharedPreferences sharePrefs;
    public static final String API_PREFS = "apiPrefs";
    private SharedPreferences.Editor editor;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        context = getApplicationContext();
        SelectListItem();
        RadioClick();

    }

    public void StartNewGameClick(View view) {
        //make new preferences
        sharePrefs = context.getSharedPreferences(API_PREFS, context.MODE_PRIVATE);
        editor = sharePrefs.edit();

        editor.putString("DIFFICULTY", radioButtonValue);
        editor.putString("CATEGORY", categoryValue);
        editor.commit();

        //go to next activity
        Intent intent = new Intent(getBaseContext(), RoundOneActivity.class);
        startActivity(intent);

    }

    //ListItemSelect
    public void SelectListItem() {
        // onclick in list
        ListView listview = (ListView) findViewById(R.id.LstSubject);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_SHORT).show();
                //match category's with the ID's
                categoryValue = SubjectHelper.subjectStringToId(((TextView) view).getText().toString());
            }

        });
    }

    //radioButton : match radioButtons with difficulty value
    public void RadioClick() {

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Easy:
                        radioButtonValue = "easy";
                        Toast.makeText(getApplicationContext(), radioButtonValue,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Medium:
                        radioButtonValue = "medium";
                        Toast.makeText(getApplicationContext(), radioButtonValue,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Hard:
                        radioButtonValue = "hard";
                        Toast.makeText(getApplicationContext(), radioButtonValue,
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}




