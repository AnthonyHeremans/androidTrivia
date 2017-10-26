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
                switch (((TextView) view).getText().toString()) {
                    case "General Knowledge":
                        categoryValue = "9";
                        break;
                    case "Entertainment: Books":
                        categoryValue = "10";
                        break;
                    case "Entertainment: Film":
                        categoryValue = "11";
                        break;
                    case "Entertainment: Music":
                        categoryValue = "12";
                        break;
                    case "Entertainment: Television":
                        categoryValue = "14";
                        break;
                    case "Science &amp; Nature":
                        categoryValue = "17";
                        break;
                    case "Science: Computer":
                        categoryValue = "18";
                        break;
                    case "Science: Mathematics":
                        categoryValue = "19";
                        break;
                    case "Mythology":
                        categoryValue = "20";
                        break;
                    case "Sports":
                        categoryValue = "21";
                        break;
                    case "Geography":
                        categoryValue = "22";
                        break;
                    case "History":
                        categoryValue = "23";
                        break;
                    case "Politics":
                        categoryValue = "24";
                        break;
                    case "Art":
                        categoryValue = "25";
                        break;
//                    case "Celebrities":
//                        categoryValue = "26";
//                        break;
//                   case "Animals":
//                        categoryValue = "27";
//                        break;
                }

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




