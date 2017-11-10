package app.be.pxl.anthonyheremans.triviagamenavigator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 11401671 on 07/11/2017.
 */

public class DataBaseHelperClass extends SQLiteOpenHelper {
    //VARIABLES FOR SEETING UP DATABASE

    //DATABASE VARIABLES
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "triviagamenavigator.db";

    //USER ACCOUNT TABLE
    public static final String TABLE_NAME_USER = "userAccount";
    public static final String COL_USER_1 = "USERID";
    public static final String COL_USER_2 = "USERNAME";
    public static final String COL_USER_3 = "PASSWORD";
    public static final String COL_USER_4 = "COINS";

    //SCORES TABLE
    public static final String TABLE_NAME_SCORES = "scores";
    public static final String COL_SCORES_1 = "SCORESID";
    public static final String COL_SCORES_2 = "USERID";
    public static final String COL_SCORES_3 = "DIFFICULTY";
    public static final String COL_SCORES_4 = "SUBJECT";
    public static final String COL_SCORES_5 = "SCORE";

    //HINTS AND ANSWERS TABLE
    public static final String TABLE_NAME_HELPERTABLE = "helperTable";
    public static final String COL_HELPERTABLE_1 = "HELPERID";
    public static final String COL_HELPERTABLE_2 = "USERID";
    public static final String COL_HELPERTABLE_3 = "HINTQ1";
    public static final String COL_HELPERTABLE_4 = "HINTQ2";
    public static final String COL_HELPERTABLE_5 = "ANSWERQ2";
    public static final String COL_HELPERTABLE_6 = "ANSWERQ3";


    //create new database
    public DataBaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //uncomment when first time making DB
        // SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //excute de query die ge wilt

        //create user table
        db.execSQL("create table " + TABLE_NAME_USER + " ("
                + COL_USER_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USER_2 + " TEXT,"
                + COL_USER_3 + " TEXT,"
                + COL_USER_4 + " INTEGER);");

        //create scores table
        db.execSQL("create table " + TABLE_NAME_SCORES + " ("
                + COL_SCORES_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_SCORES_2 + " INTEGER,"
                + COL_SCORES_3 + " TEXT,"
                + COL_SCORES_4 + " TEXT,"
                + COL_SCORES_5 + " INTEGER,"
                + " FOREIGN KEY (" + COL_SCORES_2 + ") REFERENCES " + TABLE_NAME_USER + "(" + COL_USER_1 + "));");

        //create helper table
        db.execSQL("create table " + TABLE_NAME_HELPERTABLE + " ("
                + COL_HELPERTABLE_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_HELPERTABLE_2 + " INTEGER,"
                + COL_HELPERTABLE_3 + " INTEGER,"
                + COL_HELPERTABLE_4 + " INTEGER,"
                + COL_HELPERTABLE_5 + " INTEGER,"
                + COL_HELPERTABLE_6 + " INTEGER,"
                + " FOREIGN KEY (" + COL_HELPERTABLE_2 + ") REFERENCES " + TABLE_NAME_USER + "(" + COL_USER_1 + "));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME_HELPERTABLE + "," + TABLE_NAME_SCORES + "," + TABLE_NAME_USER);
        onCreate(db);
    }

    //TODO :: seperate to other class, everything under this
    // insert
    public boolean insertUser(String username, String password) {
        //create statement
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //put values into statement
        contentValues.put(COL_USER_2, username);
        contentValues.put(COL_USER_3, password);
        contentValues.put(COL_USER_4, 0);


        //insert values into database
        //incase of error, returns -1 else row value
        long result = db.insert(TABLE_NAME_USER, null, contentValues);

        if (result != -1)
            return true;
        else return false;


    }

    public boolean insertScore(int Score, String difficulty, String subject, int userId)
    {
        //create statement
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //put values into statement
        contentValues.put(COL_SCORES_2, userId);
        contentValues.put(COL_SCORES_3, difficulty);
        contentValues.put(COL_SCORES_4, subject);
        contentValues.put(COL_SCORES_5, Score);


        //insert values into database
        //incase of error, returns -1 else row value
        long result = db.insert(TABLE_NAME_SCORES, null, contentValues);

        if (result != -1)
            return true;
        else return false;

    }

    //select
    public Cursor getUserLogin(String username, String password) {

        //create statement
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * " +
                        "from " + TABLE_NAME_USER +
                        " where " + COL_USER_2 + " like '" + username + "'" +
                        " and " + COL_USER_3 + " like '" + password + "';"
                , null);

        //cursor class is an interface
        return cursor;
    }
    public Cursor getPlayedGames(int userID, String difficulty)
    {
//create statement
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * " +
                        "from " + TABLE_NAME_SCORES +
                        " where " + COL_SCORES_2 + " = " + userID +
                        " and " + COL_SCORES_3 + " like '" + difficulty + "';"
                , null);

        //cursor class is an interface
        return cursor;
    }
}
