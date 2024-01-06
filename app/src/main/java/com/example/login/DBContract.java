package com.example.login;

import android.provider.BaseColumns;
public final class DBContract {

    private DBContract() {
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
    }
}