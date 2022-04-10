package com.example.quanlythuchi.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuchi.models.SpendType;

import java.util.ArrayList;

public class SpendTypeDAO {
    private AccountManagerSQLite accountManagerSQLite;

    public SpendTypeDAO(AccountManagerSQLite accountManagerSQLite) {
        this.accountManagerSQLite = accountManagerSQLite;
    }
    public void createSpendType(int imgSpendType, String spendTypeName, String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(accountManagerSQLite.getKeySpendTypeImg(),imgSpendType);
        values.put(accountManagerSQLite.getKeyUsername(),username);
        values.put(accountManagerSQLite.getKeyNameSpendType(),spendTypeName);
        db.insert(accountManagerSQLite.getTableTypeSpend(),null,values);
    }
    public ArrayList<SpendType> getAllSpendType(String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ accountManagerSQLite.getTableTypeSpend()
                +" where "+ accountManagerSQLite.getKeyUsername()+"="+"'"+username+"'",null);
        ArrayList<SpendType> list=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new SpendType(cursor.getInt(0),cursor.getInt(1),cursor.getString(2)));
            }while(cursor.moveToNext());
        }
        return list;
    }
    public SpendType getSpendType(String spendTypeName, String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ accountManagerSQLite.getTableTypeSpend()
                +" where "+ accountManagerSQLite.getKeyNameSpendType()+"="+"'"+spendTypeName+"'"
                +"and "+accountManagerSQLite.getKeyUsername()+"="+"'"+username+"'",null);
        cursor.moveToFirst();
        SpendType spendType=new SpendType(cursor.getInt(0),cursor.getInt(1),cursor.getString(2));
        return spendType;
    }
    public SpendType getSpendType(int spendTypeId, String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ accountManagerSQLite.getTableTypeSpend()
                +" where "+ accountManagerSQLite.getKeyIdSpendType()+"="+"'"+spendTypeId+"'"
                +"and "+accountManagerSQLite.getKeyUsername()+"="+"'"+username+"'",null);
        cursor.moveToFirst();
        SpendType spendType=new SpendType(cursor.getInt(0),cursor.getInt(1),cursor.getString(2));
        return spendType;
    }
    public void updateSpendType(String spendTypeName, String idSpendType) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(accountManagerSQLite.getKeyNameSpendType(), spendTypeName);
        db.update(accountManagerSQLite.getTableTypeSpend(), values, accountManagerSQLite.getKeyIdSpendType() + "=?", new String[]{idSpendType});
    }

    public void deleteSpendType(int idSpendType) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        db.delete(accountManagerSQLite.getTableTypeSpend(), accountManagerSQLite.getKeyIdSpendType() + "=?", new String[]{idSpendType + ""});
    }
}
