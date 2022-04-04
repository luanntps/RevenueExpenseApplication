package com.example.quanlythuchi.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuchi.models.SpendType;

import java.util.ArrayList;

public class SpendTypeDAO {
    private AccountManagerSQLite accountManagerSQLite;

    public SpendTypeDAO(AccountManagerSQLite accountManagerSQLite) {
        this.accountManagerSQLite = accountManagerSQLite;
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
}
