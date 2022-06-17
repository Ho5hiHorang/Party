package com.baewha.partywithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginJava extends AppCompatActivity {
    int version = 1;
    EditText et_id, et_pw;
    Button btn_login, btn_join;
    SQLiteDatabase database;
    DatabaseOpenHelper helper;
    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        helper = new DatabaseOpenHelper(LoginJava.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();

                if (id.length() == 0 || pw.length() == 0) {
                    Toast toast = Toast.makeText(LoginJava.this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM" + helper.tableName + "WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if (cursor.getCount() != 1) {
                    Toast toast = Toast.makeText(LoginJava.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT pw FROM" + helper.tableName + "WHERE pw = '" + pw + "'";
                cursor = database.rawQuery(sql, null);
                cursor.moveToNext();

                if (!pw.equals(cursor.getString(0))) {
                    Toast toast = Toast.makeText(LoginJava.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(LoginJava.this, "로그인 성공", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent(getApplicationContext(), MessageJava.class);
                    startActivity(intent);
                    finish();
                }
                cursor.close();
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinJava.class);
                startActivity(intent);
            }
        });
    }
}