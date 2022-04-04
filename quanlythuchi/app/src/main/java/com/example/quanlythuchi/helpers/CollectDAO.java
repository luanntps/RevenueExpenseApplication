package com.example.quanlythuchi.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.models.Collect;
import com.example.quanlythuchi.models.CollectType;
import com.example.quanlythuchi.models.SpendType;

import java.util.ArrayList;

public class CollectDAO {
    private AccountManagerSQLite accountManagerSQLite;

    public CollectDAO(AccountManagerSQLite accountManagerSQLite) {
        this.accountManagerSQLite = accountManagerSQLite;
    }

    public ArrayList<Collect> getAllCollect(String username){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ accountManagerSQLite.getTableCollect()
                +" where "+ accountManagerSQLite.getKeyUsername()+"="+"'"+username+"'",null);
        ArrayList<Collect> list=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new Collect(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5), cursor.getString(6)));
            }while(cursor.moveToNext());
        }
        return list;
    }
    public void createCollect(int collectAmount, String date, String username, String collectTypeName, String note){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        CollectTypeDAO collectTypeDAO=new CollectTypeDAO(accountManagerSQLite);
        CollectType collectType =collectTypeDAO.getCollectType(collectTypeName, username);
        int imgCollectSrc=collectType.getCollectTypeIMG();
        int idCollectType=collectType.getIdCollectType();
        ContentValues values=new ContentValues();
        values.put(accountManagerSQLite.getKeyCollectImg(),imgCollectSrc);
        values.put(accountManagerSQLite.getKeyCollectAmount(),collectAmount);
        values.put(accountManagerSQLite.getKeyUsername(),username);
        values.put(accountManagerSQLite.getKeyIdCollectType(),idCollectType);
        values.put(accountManagerSQLite.getKeyCollectNote(),note);
        values.put(accountManagerSQLite.getKeyCollectDate(),date);
        db.insert(accountManagerSQLite.getTableCollect(),null,values);
    }
    public void updateCollect(String idCollect,String username,int collectAmount, String collectTypeName, String note){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        ContentValues values=new ContentValues();
        CollectTypeDAO collectTypeDAO=new CollectTypeDAO(accountManagerSQLite);
        CollectType collectType=collectTypeDAO.getCollectType(collectTypeName,username);
        int imgCollectSrc=collectType.getCollectTypeIMG();
        int idCollectType=collectType.getIdCollectType();
        values.put(accountManagerSQLite.getKeyCollectAmount(),collectAmount);
        values.put(accountManagerSQLite.getKeyIdCollectType(),idCollectType);
        values.put(accountManagerSQLite.getKeyCollectNote(),note);
        values.put(accountManagerSQLite.getKeyCollectImg(),imgCollectSrc);
        db.update(accountManagerSQLite.getTableCollect(),values,accountManagerSQLite.getKeyCollectId()+"=?",new String[]{idCollect});
    }
    public void deleteCollect(int idCollect){
        SQLiteDatabase db=accountManagerSQLite.getReadableDatabase();
        db.delete(accountManagerSQLite.getTableCollect(),accountManagerSQLite.getKeyCollectId()+"=?",new String[]{idCollect+""});
    }
}
