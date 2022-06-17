package com.baewha.partywithme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JoinJava extends AppCompatActivity{
    EditText et_id, et_pw, et_name, et_email, et_phone;
    Button btn_join;
    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_join = (Button) findViewById(R.id.btn_join);

        helper = new DatabaseOpenHelper(JoinJava.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String phone = et_phone.getText().toString();

                if(id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    Toast toast = Toast.makeText(JoinJava.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if(cursor.getCount() != 0){
                    //존재하는 아이디입니다.
                    Toast toast = Toast.makeText(JoinJava.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    helper.insertUser(database, id, pw, name, email, phone);
                    Toast toast = Toast.makeText(JoinJava.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent(getApplicationContext(), LoginJava.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
