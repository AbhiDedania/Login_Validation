package com.example.login;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHelper DB;
    private EditText etUsername, etPassword;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button logintohome = findViewById(R.id.logintohome);
        DB = new DBHelper(this);

        logintohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        TextView gotosignup = findViewById(R.id.gotosignup);
        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check login credentials in SQLite database
        if (isValidLogin(email, password)) {
            // Redirect to home page or another activity
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidLogin(String email, String password) {
        // Initialize SQLite database
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define projection (columns to retrieve)
        String[] projection = {
                DBContract.UserEntry._ID,
                DBContract.UserEntry.COLUMN_EMAIL,
                DBContract.UserEntry.COLUMN_PASSWORD
        };


        String selection = DBContract.UserEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                DBContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isValid = false;

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASSWORD));
            isValid = password.equals(storedPassword);
            cursor.close();
        }
        db.close();
        return isValid;
    }
}