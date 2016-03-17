package com.esigelec.contentresolver;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private ContentResolver contentResolver;
    private Button btn_get_user, btn_get_user_name, btn_main2;
    private EditText editxt_id;
    private TextView txt_content;
    private static final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = getContentResolver();

        editxt_id = (EditText) findViewById(R.id.editxt_id);
        txt_content = (TextView) findViewById(R.id.txt_content);
        btn_get_user = (Button) findViewById(R.id.btn_get_user);
        btn_get_user_name = (Button) findViewById(R.id.btn_get_user_name);

        btn_get_user.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int id = Integer.valueOf(editxt_id.getText().toString());
//                String[] projection = {UserContract.Entry._ID, UserContract.Entry.COLUMN_NAME_USER_NAME, UserContract
//                        .Entry
//                        .COLUMN_NAME_ACCOUNT, UserContract.Entry.COLUMN_NAME_PASSWORD, UserContract.Entry
//                        .COLUMN_NAME_EMAIL, UserContract.Entry.COLUMN_NAME_ADDRESS};
//                String selection = UserContract.Entry._ID + " LIKE ?";
//                String[] selectionArgs = {String.valueOf(id)};
                Uri uri = Uri.parse(UserContract.Entry.SCHEMA + UserContract.Entry.AUTHORITY + "/user/" + id);
                Log.i(TAG, "--URI-->" + uri.toString());
                Cursor cursor = contentResolver.query(uri, null, null, null, null);

                if (cursor.getCount() <= 0)
                {
                    txt_content.setText("Cannot get the user!");
                    return;
                }

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

                StringBuilder sb = new StringBuilder();
                sb.append(user_name);
                sb.append(user_id);
                sb.append(email);
                sb.append(address);
                sb.append(account);
                sb.append(password);
                txt_content.setText(sb.toString());
            }
        });

        btn_get_user_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int id = Integer.valueOf(editxt_id.getText().toString());
//                String[] projection = {UserContract.Entry._ID, UserContract.Entry.COLUMN_NAME_USER_NAME, UserContract
//                        .Entry
//                        .COLUMN_NAME_ACCOUNT, UserContract.Entry.COLUMN_NAME_PASSWORD, UserContract.Entry
//                        .COLUMN_NAME_EMAIL, UserContract.Entry.COLUMN_NAME_ADDRESS};
//                String selection = UserContract.Entry._ID + " LIKE ?";
//                String[] selectionArgs = {String.valueOf(id)};
                Uri uri = Uri.parse(UserContract.Entry.SCHEMA + UserContract.Entry.AUTHORITY + "/user/" + id + "/name");
                Cursor cursor = contentResolver.query(uri, null, null, null, null);

                if (cursor.getCount() <= 0)
                {
                    txt_content.setText("Cannot get the user!");
                    return;
                }

                cursor.moveToFirst();
                // only get the first one
                int num_col_name = cursor.getColumnIndex(UserContract.Entry.COLUMN_NAME_USER_NAME);

                String user_name = cursor.getString(num_col_name);

                StringBuilder sb = new StringBuilder();
                sb.append(user_name + "\n");


                txt_content.setText(sb.toString());

            }
        });

        btn_main2 = (Button) findViewById(R.id.btn_main2);
        btn_main2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

    }
}
