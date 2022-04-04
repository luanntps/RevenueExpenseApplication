package com.example.quanlythuchi.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.models.CollectType;
import com.example.quanlythuchi.models.Spend;
import com.example.quanlythuchi.models.SpendType;

import java.util.ArrayList;

public class SpendDAO {
    private AccountManagerSQLite accountManagerSQLite;
    public SpendDAO(AccountManagerSQLite accountManagerSQLite) {
        this.accountManagerSQLite = accountManagerSQLite;
    }
    public ArrayList<Spend> getAllSpend(String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ accountManagerSQLite.getTableSpend()
                +" where "+ accountManagerSQLite.getKeyUsername()+"="+"'"+username+"'",null);
        ArrayList<Spend> list=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new Spend(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getString(6)));
            }while(cursor.moveToNext());
        }
        return list;
    }
    public void createSpend(int spendingAmount, String date, String username, String spendTypeName, String note){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        SpendTypeDAO spendTypeDAO=new SpendTypeDAO(accountManagerSQLite);
        SpendType spendType =spendTypeDAO.getSpendType(spendTypeName,username);
        int imgSpendSrc=spendType.getSpendTypeIMG();
        int idSpendType=spendType.getIdSpendType();
        ContentValues values=new ContentValues();
        values.put(accountManagerSQLite.getKeySpendAmount(),spendingAmount);
        values.put(accountManagerSQLite.getKeyUsername(),username);
        values.put(accountManagerSQLite.getKeySpendImg(),imgSpendSrc);
        values.put(accountManagerSQLite.getKeyIdSpendType(),idSpendType);
        values.put(accountManagerSQLite.getKeySpendNote(),note);
        values.put(accountManagerSQLite.getKeyUsername(),username);
        values.put(accountManagerSQLite.getKeySpendDate(),date);
        db.insert(accountManagerSQLite.getTableSpend(),null,values);
    }
    public void updateSpend( String idSpend,String username, int spendAmount,String spendTypeName, String note){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        ContentValues values=new ContentValues();
        SpendTypeDAO spendTypeDAO=new SpendTypeDAO(accountManagerSQLite);
        SpendType spendType=spendTypeDAO.getSpendType(spendTypeName,username);
        int imgSpendSrc=spendType.getSpendTypeIMG();
        int idSpendType=spendType.getIdSpendType();
        values.put(accountManagerSQLite.getKeySpendAmount(),spendAmount);
        values.put(accountManagerSQLite.getKeyIdSpendType(),idSpendType);
        values.put(accountManagerSQLite.getKeySpendNote(),note);
        values.put(accountManagerSQLite.getKeySpendImg(),imgSpendSrc);
        db.update(accountManagerSQLite.getTableSpend(),values,accountManagerSQLite.getKeySpendId()+"=?",new String[]{idSpend});
    }
    public void deleteSpend(int idSpend){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        db.delete(accountManagerSQLite.getTableSpend(),accountManagerSQLite.getKeySpendId()+"=?",new String[]{idSpend+""});
    }
}
