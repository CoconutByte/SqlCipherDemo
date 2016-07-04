package com.example.ryushiki.sqlcipherdemo;

import net.sqlcipher.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSQLCipher();
    }

    private void initializeSQLCipher() {
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath("demo.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "test123", null);
        database.execSQL("CREATE TABLE Persons\n" +
                "(\n" +
                "Age TEXT,\n" +
                "Name TEXT\n" +
                ");");
        database.execSQL("insert into Persons(Age, Name) values(?, ?)", new Object[]{"22",
                "Tom"});
        database.execSQL("insert into Persons(Age, Name) values(?, ?)", new Object[]{"24",
                "Tom"});
        database.close();
    }

    public int getMinAgeOfPerson() {
        int result = -1;
        File databaseFile = getDatabasePath("demo.db");
        database = SQLiteDatabase.openOrCreateDatabase(databaseFile,"test123",null);
        Cursor cursor = database.rawQuery("select min(Age) from Persons", null);
        if (cursor == null) {
            database.close();
            return result;
        }
        while(cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        database.close();
        return result;
    }

    public void clickButton(View view) {
        Toast.makeText(MainActivity.this, String.valueOf(getMinAgeOfPerson()), Toast.LENGTH_SHORT).show();
    }
}
