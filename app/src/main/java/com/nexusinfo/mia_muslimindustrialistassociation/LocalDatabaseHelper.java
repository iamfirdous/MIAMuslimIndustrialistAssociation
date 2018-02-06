package com.nexusinfo.mia_muslimindustrialistassociation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nexusinfo.mia_muslimindustrialistassociation.models.UserModel;

/**
 * Created by firdous on 11/28/2017.
 */

public class LocalDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LocalDB";

    private static final String TABLE_NAME = "LocalTB";

    private static final String COLUMN_MEMBER_ID = "MemberID";
    private static final String COLUMN_AUTH = "Authentication";
    private static final String COLUMN_MEMBER_MOBILE = "MemberMobile";
    private static final String COLUMN_MEMBER_EMAIL = "MemberEmail";
    private static final String COLUMN_MEMBER_NAME = "MemberName";
    private static final String COLUMN_COMPANY_ID = "CompanyID";
    private static final String COLUMN_BRANCH_CODE = "BranchCode";

    private static LocalDatabaseHelper mInstance;

    public static LocalDatabaseHelper getInstance(Context context) {

        if(mInstance == null){
            mInstance = new LocalDatabaseHelper(context);
        }
        return mInstance;
    }


    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " " +
                "( " + COLUMN_MEMBER_ID + " INTEGER PRIMARY KEY," +
                " " + COLUMN_AUTH + " TEXT," +
                " " + COLUMN_MEMBER_MOBILE + " TEXT," +
                " " + COLUMN_MEMBER_EMAIL + " TEXT," +
                " " + COLUMN_MEMBER_NAME + " TEXT," +
                " " + COLUMN_COMPANY_ID + " TEXT," +
                " " + COLUMN_BRANCH_CODE + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEMBER_ID, user.getMemberId());
        cv.put(COLUMN_AUTH, user.getAuth());
        cv.put(COLUMN_MEMBER_MOBILE, user.getMemberMobile());
        cv.put(COLUMN_MEMBER_EMAIL, user.getMemberEmail());
        cv.put(COLUMN_MEMBER_NAME, user.getMemberName());
        cv.put(COLUMN_COMPANY_ID, user.getCmpId());
        cv.put(COLUMN_BRANCH_CODE, user.getBrCode());

        return (db.insert(TABLE_NAME, null, cv) != -1);
    }

    public boolean isDataExist(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return (cursor.getCount() > 0);
    }

    public UserModel getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();

        UserModel user = new UserModel();
        user.setMemberId(cursor.getInt(0));
        user.setAuth(cursor.getString(1));
        user.setMemberMobile(cursor.getString(2));
        user.setMemberEmail(cursor.getString(3));
        user.setMemberName(cursor.getString(4));
        user.setCmpId(cursor.getString(5));
        user.setBrCode(cursor.getString(6));

        return user;
    }

    public boolean deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        return true;
    }
}
