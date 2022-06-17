package com.baewha.partywithme;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String tableName = "usertbl";
    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + tableName + " (id CHAR(20) PRIMARY KEY, pw CHAR(20), name CHAR(20), email CHAR(30), phone CHAR(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate((db));
    }

    public void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + tableName + "(id char(20), pw char(20), name char(20), email char(30), phone char(30))";
        try{
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void insertUser(SQLiteDatabase db, String id, String pw, String name, String email, String phone){
        Log.i("tag", "회원가입을 했을 때 실행함");
        db.beginTransaction();
        try{
            String sql = "INSERT INTO" + tableName + "(id, pw, name, email, phone)"
                    + "values('" + id + "', '" + pw + "', '" + name + "', '" + email + "', '" + phone + "')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}
