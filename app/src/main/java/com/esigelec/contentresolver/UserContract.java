package com.esigelec.contentresolver;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by enzoz on 2016/3/11.
 */
public final class UserContract
{
    public UserContract()
    {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
    }

    public static abstract class Entry implements BaseColumns
    {
        public static final String TABLE_NAME = "User";
        //public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_ACCOUNT = "account";
        public static final String COLUMN_NAME_PASSWORD = "password";

        // Authority
        public static final String AUTHORITY = "com.example.enzo.practicedemos.DB.Providers.UserProvider";

        // Path
        public static final String PATH_ALL_USER = "user";
        public static final String PATH_USER = "user/#";
        public static final String PATH_USER_NAME = "user/#/name";
        public static final String PATH_USER_ADDRESS = "user/#/address";
        public static final String PATH_USER_EMAIL = "user/#/email";
        public static final String PATH_USER_ACCOUNT = "user/#/account";
        public static final String PATH_USER_PASSWORD = "user/#/password";

        // Uri
        public static final String SCHEMA = "content://";
        public static final Uri URI_ALL_USER = Uri.parse(SCHEMA + AUTHORITY + "/" + PATH_ALL_USER);
        public static final Uri URI_USER = Uri.parse(SCHEMA + AUTHORITY + "/" + PATH_USER);
        public static final Uri URI_USER_NAME = Uri.parse(SCHEMA + AUTHORITY + "/" + PATH_USER_NAME);
        public static final Uri URI_USER_ADDRESS = Uri.parse(SCHEMA + AUTHORITY + "/" + PATH_USER_ADDRESS);
        public static final Uri URI_USER_EMAIL = Uri.parse(SCHEMA + AUTHORITY + "/" + PATH_USER_EMAIL);
        public static final Uri URI_USER_ACCOUNT = Uri.parse(SCHEMA + AUTHORITY + "/" + PATH_USER_ACCOUNT);
        public static final Uri URI_USER_PASSWORD = Uri.parse(SCHEMA + AUTHORITY + "/" + PATH_USER_PASSWORD);

    }
}
