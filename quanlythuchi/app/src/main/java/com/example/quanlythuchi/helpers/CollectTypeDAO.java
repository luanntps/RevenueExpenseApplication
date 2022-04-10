package com.example.quanlythuchi.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlythuchi.models.CollectType;
import com.example.quanlythuchi.models.SpendType;

import java.util.ArrayList;

public class CollectTypeDAO {
    private AccountManagerSQLite accountManagerSQLite;

    public CollectTypeDAO(AccountManagerSQLite accountManagerSQLite) {
        this.accountManagerSQLite = accountManagerSQLite;
    }

    public void createCollectType(int imgCollectType, String collectTypeName, String username) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(accountManagerSQLite.getKeyCollectTypeImg(), imgCollectType);
        values.put(accountManagerSQLite.getKeyUsername(), username);
        values.put(accountManagerSQLite.getKeyNameCollectType(), collectTypeName);
        db.insert(accountManagerSQLite.getTableTypeCollect(), null, values);
    }

    public ArrayList<CollectType> getAllCollectType(String username) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from " + accountManagerSQLite.getTableTypeCollect()
                + " where " + accountManagerSQLite.getKeyUsername() + "=" + "'" + username + "'", null);
        ArrayList<CollectType> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new CollectType(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public CollectType getCollectType(String collectTypeName, String username) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from " + accountManagerSQLite.getTableTypeCollect()
                + " where " + accountManagerSQLite.getKeyNameCollectType() + "=" + "'" + collectTypeName + "'"
                + "and " + accountManagerSQLite.getKeyUsername() + "=" + "'" + username + "'", null);
        cursor.moveToFirst();
        CollectType collectType = new CollectType(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        return collectType;
    }

    public CollectType getCollectType(int collectTypeId, String username) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from " + accountManagerSQLite.getTableTypeCollect()
                + " where " + accountManagerSQLite.getKeyIdCollectType() + "=" + "'" + collectTypeId + "'"
                + "and " + accountManagerSQLite.getKeyUsername() + "=" + "'" + username + "'", null);
        cursor.moveToFirst();
        cursor.moveToFirst();
        CollectType collectType = new CollectType(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        return collectType;
    }

    public void updateCollectType(String collectTypeName, String idCollectType) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(accountManagerSQLite.getKeyNameCollectType(), collectTypeName);
        db.update(accountManagerSQLite.getTableTypeCollect(), values, accountManagerSQLite.getKeyIdCollectType() + "=?", new String[]{idCollectType});
    }

    public void deleteCollectType(int idCollectType) {
        SQLiteDatabase db = accountManagerSQLite.getReadableDatabase();
        db.delete(accountManagerSQLite.getTableTypeCollect(), accountManagerSQLite.getKeyIdCollectType() + "=?", new String[]{idCollectType + ""});
    }
}
