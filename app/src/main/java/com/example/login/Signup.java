package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Signup extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    private Button signuptologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button signuptologin = findViewById(R.id.signuptologin);

        signuptologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        TextView gotosignup = findViewById(R.id.gotosignup);
        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void signUp() {
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern) )
            {
                Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();

            }
        else
            {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            }

        if (password.equals(confirmPassword) && isValidPassword(password)) {
            saveUserData(email, password);

            Intent intent = new Intent(Signup.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid password or passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()_+].*");
    }

    private void saveUserData(String email, String password) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.COLUMN_EMAIL, email);
        values.put(DBContract.UserEntry.COLUMN_PASSWORD, password);

        long newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values);

        db.close();
    }
}