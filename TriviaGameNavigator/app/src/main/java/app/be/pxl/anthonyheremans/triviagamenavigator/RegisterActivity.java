package app.be.pxl.anthonyheremans.triviagamenavigator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.be.pxl.anthonyheremans.triviagamenavigator.Database.DataBaseHelperClass;

public class RegisterActivity extends AppCompatActivity {
    DataBaseHelperClass myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DataBaseHelperClass(this);
    }

    public void MakeNewAccountClick(View view) {

        EditText usernameO = (EditText) findViewById(R.id.txtUsername);
        String usernameS = usernameO.getText().toString();
        EditText passwordO = (EditText) findViewById(R.id.txtPassword);
        String passwordS = passwordO.getText().toString();

        if (!usernameS.trim().equals("") && !passwordS.trim().equals("")) {
            try {
                boolean isInserted = myDb.insertUser(usernameS, passwordS);
                if (isInserted) {
                    Toast.makeText(getApplicationContext(), "Username is succesfully made",
                            Toast.LENGTH_SHORT).show();
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong with creating user",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getApplicationContext(), "Username or Password are empty",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
