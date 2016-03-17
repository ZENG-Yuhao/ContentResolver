package com.esigelec.contentresolver;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final String URI_USER = UserContract.Entry.SCHEMA + UserContract.Entry.AUTHORITY + "/" +
            UserContract.Entry.PATH_USER;
    private static final String URI_USER_NAME = UserContract.Entry.SCHEMA + UserContract.Entry.AUTHORITY + "/" +
            UserContract.Entry.PATH_USER_NAME;

    private static final int ID_LOADER_USER = 0;
    private static final int ID_LOADER_USER_NAME = 1;

    private static final String[] projection_user = {
            UserContract.Entry._ID,
            UserContract.Entry.COLUMN_NAME_USER_NAME,
            UserContract.Entry.COLUMN_NAME_EMAIL,
            UserContract.Entry.COLUMN_NAME_ADDRESS,
            UserContract.Entry.COLUMN_NAME_ACCOUNT,
            UserContract.Entry.COLUMN_NAME_PASSWORD
    };

    private static final String[] projection_user_name = {
            UserContract.Entry.COLUMN_NAME_USER_NAME
    };

    private EditText editxt_id, editxt_name, editxt_address, editxt_email, editxt_account, editxt_password;
    private Button btn_modify_password, btn_modify_password2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // !!! when Data-changed-notification has came, Loader will not recieve this msg, instead, we should register a
        // ContentObserver to supervise it.
        Uri uri = Uri.parse(URI_USER);
        getContentResolver().registerContentObserver(uri, true, new MyOvserver(new UiRefreshHandler()));

        getLoaderManager().initLoader(ID_LOADER_USER, null, this);
        //getLoaderManager().initLoader(ID_LOADER_USER_NAME, null, this);

        editxt_id = (EditText) findViewById(R.id.editxt_id);
        editxt_name = (EditText) findViewById(R.id.editxt_name);
        editxt_address = (EditText) findViewById(R.id.editxt_address);
        editxt_email = (EditText) findViewById(R.id.editxt_email);
        editxt_account = (EditText) findViewById(R.id.editxt_account);
        editxt_password = (EditText) findViewById(R.id.editxt_password);
        btn_modify_password = (Button) findViewById(R.id.btn_modify_password);
        btn_modify_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContentValues values = new ContentValues();
                values.put(UserContract.Entry.COLUMN_NAME_PASSWORD, "password1");
                String url_user = URI_USER.replace("#", "2");
                Uri uri = Uri.parse(url_user);
                String selection = UserContract.Entry._ID + " LIKE ?";
                String[] selectionArgs = {String.valueOf(2)};
                getContentResolver().update(uri, values, selection, selectionArgs);
            }
        });


        btn_modify_password2 = (Button) findViewById(R.id.btn_modify_password2);
        btn_modify_password2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContentValues values = new ContentValues();
                values.put(UserContract.Entry.COLUMN_NAME_PASSWORD, "password2");
                String url_user = URI_USER.replace("#", "2");
                Uri uri = Uri.parse(url_user);
                String selection = UserContract.Entry._ID + " LIKE ?";
                String[] selectionArgs = {String.valueOf(2)};
                getContentResolver().update(uri, values, selection, selectionArgs);
            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        // Build the CursorLoader using its constructor method, which requires the complete set of information needed
        // to perform a query to the ## ContentProvider ##.
        switch (id)
        {
            case ID_LOADER_USER:
                // "this" equals to "Main2Activity.this".
                String url_user = URI_USER.replace("#", "2");
                Uri uri_user = Uri.parse(url_user);
                // Provider will deal all transactions through uri, there no need to pass params such as projection.
                return new CursorLoader(this, uri_user, null, null, null, null);
            case ID_LOADER_USER_NAME:
                String url_user_name = URI_USER_NAME.replace("#", "2");
                Uri uri_user_name = Uri.parse(url_user_name);
                return new CursorLoader(this, uri_user_name, null, null, null, null);
        }
        return null;
    }

    // !!! when Data-changed-notification has came, Loader will not recieve this msg, instead, we should register a
    // ContentObserver to supervise it.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        switch (loader.getId())
        {
            case ID_LOADER_USER:
                Log.i("ID_LOADER_USER", "---->onLoadFinished. ID_LOADER_USER:" + ID_LOADER_USER);
                refreshUI(data);
                break;
            case ID_LOADER_USER_NAME:
                Log.i("ID_LOADER_USER", "---->onLoadFinished. ID_LOADER_USER_NAME:" + ID_LOADER_USER_NAME);
                break;
            default:
                Log.i("ID_LOADER_USER", "---->onLoadFinished. " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {

    }

    private void refreshUI(Cursor cursor)
    {
        User user = getFirstUser(cursor);
        editxt_id.setText(user.getId());
        editxt_name.setText(user.getName());
        editxt_address.setText(user.getAddress());
        editxt_email.setText(user.getEmail());
        editxt_account.setText(user.getAccount());
        editxt_password.setText(user.getPassword());
    }

    public User getFirstUser(Cursor cursor)
    {
        if (cursor.getCount() <= 0)
            return null;

        cursor.moveToFirst();
        // only get the first one
        int num_col_id = cursor.getColumnIndex(UserContract.Entry._ID);
        int num_col_name = cursor.getColumnIndex(UserContract.Entry.COLUMN_NAME_USER_NAME);
        int num_col_email = cursor.getColumnIndex(UserContract.Entry.COLUMN_NAME_EMAIL);
        int num_col_address = cursor.getColumnIndex(UserContract.Entry.COLUMN_NAME_ADDRESS);
        int num_col_account = cursor.getColumnIndex(UserContract.Entry.COLUMN_NAME_ACCOUNT);
        int num_col_password = cursor.getColumnIndex(UserContract.Entry.COLUMN_NAME_PASSWORD);

        String user_id = cursor.getString(num_col_id);
        String user_name = cursor.getString(num_col_name);
        String email = cursor.getString(num_col_email);
        String address = cursor.getString(num_col_address);
        String account = cursor.getString(num_col_account);
        String password = cursor.getString(num_col_password);

        User user = new User(user_id, user_name, address, email, account, password);
        cursor.close();
        return user;
    }

    private class MyOvserver extends ContentObserver
    {
        Handler handler;

        public MyOvserver(Handler handler)
        {
            super(handler);
            this.handler = handler;
        }

        @Override
        public void onChange(boolean selfChange)
        {
            handler.sendEmptyMessage(1);
        }
    }

    private class UiRefreshHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                // query new data
                String url_user = URI_USER.replace("#", "2");
                Uri uri_user = Uri.parse(url_user);
                Cursor cursor = getContentResolver().query(uri_user, null, null, null, null);
                // refresh ui
                refreshUI(cursor);
                Log.i("ContentObserver", "---->onChange.");
            }
        }
    }
}
