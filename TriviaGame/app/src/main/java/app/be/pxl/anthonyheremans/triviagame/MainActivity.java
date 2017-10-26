package app.be.pxl.anthonyheremans.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import app.be.pxl.anthonyheremans.triviagame.DataBaseHelper.DataBaseHelperClass;
import app.be.pxl.anthonyheremans.triviagame.DomainClasses.User;


public class MainActivity extends AppCompatActivity {
    // declare database helper
    DataBaseHelperClass myDb;

    //shared preference for User
    SharedPreferences sharePrefs;
    static final String USER_PREFS = "userPrefs";
    SharedPreferences.Editor editor;
    Context context;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        //will create the database
        myDb = new DataBaseHelperClass(this);
    }

    public void registerClick(View view) {
        Intent startRegisterActivity = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(startRegisterActivity);
    }

    /**
     * Called when the user touches the button
     */
    public void loginClick(View view) {
        //Getting Values from Login
        EditText loginV = (EditText) findViewById(R.id.TxtLogin);
        String loginS = loginV.getText().toString();
        EditText passwordV = (EditText) findViewById(R.id.TxtPassword);
        String passwordS = passwordV.getText().toString();


        //Get Label For error messages
        TextView errorMessage = (TextView) findViewById(R.id.lblErrorMessage);

        //Check Empty Strings and check if login is true
        if (loginS.trim().equals("") && passwordS.trim().equals("")) {
            errorMessage.setText("Login and password are empty, enter your credits");
        } else if (loginS.trim().equals("")) {
            errorMessage.setText("Login is empty, Enter valid login");
        } else if (passwordS.trim().equals("")) {
            errorMessage.setText("Password is empty, Enter valid password");
        } else {
            //get login from db
            Cursor result = myDb.getUserLogin(loginS, passwordS);
            if (result.getCount() == 0) {
                errorMessage.setText("This user doesn't exsist , please try again");
                return;
            } else {
                //put data into a buffer
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {

                    int id  = Integer.parseInt(result.getString(0));
                    String username = result.getString(1);
                    String password = result.getString(2);
                    int coins  = Integer.parseInt(result.getString(3));

                    //make user object from loged in user
                    try {

                        user = new User(id,username,password,coins);

                        //make new preferences
                        sharePrefs = context.getSharedPreferences(USER_PREFS, context.MODE_PRIVATE);
                        editor = sharePrefs.edit();
                        //make a json object of the user to pass it trough shared prefs
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        editor.putString("USEROBJECT", json);
                        editor.commit();

                    } catch (NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                }
                Intent startHomeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(startHomeActivity);
            }

        }
    }
}
