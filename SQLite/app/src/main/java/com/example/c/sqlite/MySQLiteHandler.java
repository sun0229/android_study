package com.example.c.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by c on 2015-01-18.
 */
public class MySQLiteHandler {
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    public MySQLiteHandler(Context context){
        helper = new MySQLiteOpenHelper(context,"person.sqlite",null,1);
    }

    public void insert(String name, int age, String address){
        //String sql = "insert into student values (null,'"+name+"',"+age+",'"+address+"')";
        //db.execSQL(sql);

        ContentValues value = new ContentValues();
        value.put("name",name);
        value.put("age",age);
        value.put("address",address);

        db = helper.getWritableDatabase();
        db.insert("student",null,value);
    }

    public void updateAge(String name, int newAge){
        ContentValues values = new ContentValues();
        values.put("age",newAge);
        db = helper.getWritableDatabase();
        db.update("student",values,"name = ?", new String[]{name});
    }

    public void delete(String name){
        db = helper.getWritableDatabase();
        db.delete("student","name = ?", new String[]{name});
    }

    public String getAllData(){
        String res = "";

        db = helper.getReadableDatabase();
        Cursor c = db.query("student",null,null,null,null,null,null);

        //String sql = "select * from student";
        //Cursor c = db.rawQuery(sql,null);

        while(c.moveToNext()){
            int age = c.getInt(c.getColumnIndex("age"));
            String name = c.getString(c.getColumnIndex("name"));
            String address = c.getString(c.getColumnIndex("address"));
            int id = c.getInt(c.getColumnIndex("id"));

            res += "id : "+id+" / name : " +name+" / age : "+age+" / address : "+address+"\n";
        }

        return res;
    }
}
