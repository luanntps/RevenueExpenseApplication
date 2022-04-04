package com.example.quanlythuchi.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuchi.models.User;

import java.util.ArrayList;

public class UserDAO {
    private AccountManagerSQLite accountManagerSQLite;

    public UserDAO(AccountManagerSQLite accountManagerSQLite) {
        this.accountManagerSQLite = accountManagerSQLite;
    }
    public ArrayList<User> getAllUser(){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ accountManagerSQLite.getTableUser(),null);
        ArrayList<User> list=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new User(cursor.getString(0)));
            }while(cursor.moveToNext());
        }
        return list;
    }
    public void createUser(String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(accountManagerSQLite.getKeyUsername(),username);
        db.insert(accountManagerSQLite.getTableUser(),null,values);
    }
    public void deleteUser(String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        db.delete(accountManagerSQLite.getTableUser(),accountManagerSQLite.getKeyUsername()+"=?",new String[]{username});
        db.delete(accountManagerSQLite.getTableSpend(),accountManagerSQLite.getKeyUsername()+"=?",new String[]{username});
        db.delete(accountManagerSQLite.getTableCollect(),accountManagerSQLite.getKeyUsername()+"=?",new String[]{username});
        db.delete(accountManagerSQLite.getTableTypeCollect(),accountManagerSQLite.getKeyUsername()+"=?",new String[]{username});
        db.delete(accountManagerSQLite.getTableTypeSpend(),accountManagerSQLite.getKeyUsername()+"=?",new String[]{username});
    }
}
