package com.example.quanlythuchi.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quanlythuchi.R;

import java.util.ArrayList;

public class AccountManagerSQLite extends SQLiteOpenHelper {
    SQLiteDatabase db=this.getReadableDatabase();
    private static final String DATABASE_NAME="AccountManager";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_USER="user";
    private static final String KEY_USERNAME="userName";

    private static final String TABLE_SPEND="spend";
    private static final String KEY_SPEND_ID="spendID";
    private static final String KEY_SPEND_IMG="spendImg";
    private static final String KEY_SPEND_AMOUNT="spendAmount";
    private static final String KEY_SPEND_DATE="spendDate";
    private static final String KEY_SPEND_NOTE="spendNote";

    private static final String TABLE_COLLECT="collect";
    private static final String KEY_COLLECT_ID="collectID";
    private static final String KEY_COLLECT_IMG="collectImg";
    private static final String KEY_COLLECT_AMOUNT="collectAmount";
    private static final String KEY_COLLECT_DATE="collectDate";
    private static final String KEY_COLLECT_NOTE="collectNote";

    private static final String TABLE_TYPE_SPEND="typeSpend";
    private static final String KEY_SPEND_TYPE_IMG="typeSpendIMG";
    private static final String KEY_ID_SPEND_TYPE="typeSpendID";
    private static final String KEY_NAME_SPEND_TYPE="typeSpendName";

    private static final String TABLE_TYPE_COLLECT="typeCollect";
    private static final String KEY_COLLECT_TYPE_IMG="typeCollectIMG";
    private static final String KEY_ID_COLLECT_TYPE="typeCollectID";
    private static final String KEY_NAME_COLLECT_TYPE="typeCollectName";

    public AccountManagerSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUser="create table "+TABLE_USER+"("
                +KEY_USERNAME+" text primary key"+")";
        db.execSQL(sqlUser);
        String sqlCollect="create table "+TABLE_COLLECT+"("
                +KEY_COLLECT_ID+" integer primary key autoincrement"+","
                +KEY_COLLECT_IMG+" integer"+","
                +KEY_COLLECT_AMOUNT+" integer"+","
                +KEY_COLLECT_DATE+ " date"+","
                +KEY_USERNAME+" text"+","
                +KEY_ID_COLLECT_TYPE+" integer"+","
                +KEY_COLLECT_NOTE+" text"+")";
        db.execSQL(sqlCollect);

        String sqlSpend="create table "+TABLE_SPEND+"("
                +KEY_SPEND_ID+" integer primary key autoincrement"+","
                +KEY_SPEND_IMG+" integer"+","
                +KEY_SPEND_AMOUNT+" integer"+","
                +KEY_SPEND_DATE+ " date"+","
                +KEY_USERNAME+" text"+","
                +KEY_ID_SPEND_TYPE+" integer"+","
                +KEY_SPEND_NOTE+" text"+")";
        db.execSQL(sqlSpend);

        String sqlTypeSpend= "create table "+TABLE_TYPE_SPEND+"("
                +KEY_ID_SPEND_TYPE+" integer primary key autoincrement"+","
                +KEY_SPEND_TYPE_IMG+" integer"+","
                +KEY_NAME_SPEND_TYPE+" text"+","
                +KEY_USERNAME+" text"+")";
        db.execSQL(sqlTypeSpend);

        String sqlTypeCollect= "create table "+TABLE_TYPE_COLLECT+"("
                +KEY_ID_COLLECT_TYPE+" integer primary key autoincrement"+","
                +KEY_COLLECT_TYPE_IMG+" integer"+","
                +KEY_NAME_COLLECT_TYPE+" text"+","
                +KEY_USERNAME+" text"+")";
        db.execSQL(sqlTypeCollect);
    }
    public void createDefaultSpendTypeAndCollectType(String username){
        String sqlAddTypeSpend= "insert into "+TABLE_TYPE_SPEND+"("+KEY_SPEND_TYPE_IMG+","+KEY_NAME_SPEND_TYPE+","+KEY_USERNAME+")"
                +" values"+"("+"'"+R.drawable.ic_travel+"'"+","+"'Di chuyển'"+","+"'"+username+"'"+")"+","+"("+"'"+R.drawable.ic_clothes+"'"+","+"'Quần áo'"+","+"'"+username+"'"+")"
                +","+"("+"'"+R.drawable.ic_food+"'"+","+"'Ăn uống'"+","+"'"+username+"'"+")"+","+"("+"'"+R.drawable.ic_homepay+"'"+","+"'Tiền nhà'"+","+"'"+username+"'"+")"
                +","+"("+"'"+R.drawable.ic_payment+"'"+","+"'Tiền đóng'"+","+"'"+username+"'"+")"+","+"("+"'"+R.drawable.ic_tuition+"'"+","+"'Học phí'"+","+"'"+username+"'"+")"
                +","+"("+"'"+R.drawable.ic_other_spend+"'"+","+"'Khác'"+","+"'"+username+"'"+")";
        db.execSQL(sqlAddTypeSpend);

        String sqlAddTypeCollect= "insert into "+TABLE_TYPE_COLLECT+"("+KEY_COLLECT_TYPE_IMG+","+KEY_NAME_COLLECT_TYPE+","+KEY_USERNAME+")"
                +" values"+"("+"'"+R.drawable.ic_save_money+"'"+","+"'Tiết kiệm'"+","+"'"+username+"'"+")"+","+"("+"'"+R.drawable.ic_salary+"'"+","+"'Lương tháng'"+","+"'"+username+"'"+")"
                +","+"("+"'"+R.drawable.ic_investment+"'"+","+"'Đầu tư'"+","+"'"+username+"'"+")"+","+"("+"'"+R.drawable.ic_insurance+"'"+","+"'Bảo hiểm'"+","+"'"+username+"'"+")"
                +","+"("+"'"+R.drawable.ic_revenue+"'"+","+"'Doanh thu'"+","+"'"+username+"'"+")"+","+"("+"'"+R.drawable.ic_other_collect+"'"+","+"'Khác'"+","+"'"+username+"'"+")";
        db.execSQL(sqlAddTypeCollect);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("drop table if exists "+TABLE_USER);
        onCreate(db);
        db.execSQL("drop table if exists " +TABLE_COLLECT);
        onCreate(db);
        db.execSQL("drop table if exists " +TABLE_SPEND);
        onCreate(db);
        db.execSQL("drop table if exists " +TABLE_TYPE_COLLECT);
        onCreate(db);
        db.execSQL("drop table if exists " +TABLE_TYPE_SPEND);
        onCreate(db);
    }

    public static String getTableUser() {
        return TABLE_USER;
    }

    public static String getKeyUsername() {
        return KEY_USERNAME;
    }

    public static String getTableSpend() {
        return TABLE_SPEND;
    }

    public static String getKeySpendId() {
        return KEY_SPEND_ID;
    }

    public static String getKeySpendAmount() {
        return KEY_SPEND_AMOUNT;
    }

    public static String getKeySpendDate() {
        return KEY_SPEND_DATE;
    }

    public static String getKeySpendNote() {
        return KEY_SPEND_NOTE;
    }

    public static String getTableCollect() {
        return TABLE_COLLECT;
    }

    public static String getKeyCollectId() {
        return KEY_COLLECT_ID;
    }

    public static String getKeyCollectAmount() {
        return KEY_COLLECT_AMOUNT;
    }

    public static String getKeyCollectDate() {
        return KEY_COLLECT_DATE;
    }

    public static String getKeyCollectNote() {
        return KEY_COLLECT_NOTE;
    }

    public static String getTableTypeSpend() {
        return TABLE_TYPE_SPEND;
    }

    public static String getKeyIdSpendType() {
        return KEY_ID_SPEND_TYPE;
    }

    public static String getKeyNameSpendType() {
        return KEY_NAME_SPEND_TYPE;
    }

    public static String getTableTypeCollect() {
        return TABLE_TYPE_COLLECT;
    }

    public static String getKeyIdCollectType() {
        return KEY_ID_COLLECT_TYPE;
    }

    public static String getKeyNameCollectType() {
        return KEY_NAME_COLLECT_TYPE;
    }

    public static String getKeySpendImg() {
        return KEY_SPEND_IMG;
    }

    public static String getKeyCollectImg() {
        return KEY_COLLECT_IMG;
    }

}
