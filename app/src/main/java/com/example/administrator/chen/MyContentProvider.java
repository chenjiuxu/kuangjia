package com.example.administrator.chen;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Administrator on 2015/3/24.
 */
public class MyContentProvider extends ContentProvider {
    public static String CHEN_TEST = "com.example.administrator.chen.MyContentProvider";
    private openDB opendb;//数据库帮助类
    private static UriMatcher um = new UriMatcher(UriMatcher.NO_MATCH);//uri筛选器
    private static final int tag1 = 1;
    private static final int tag2 = 2;

    static {
        um.addURI(CHEN_TEST, "test", tag1);
        um.addURI(CHEN_TEST, "test/*", tag2);
    }
    @Override
    public boolean onCreate() {
        opendb = new openDB(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase datasql = opendb.getReadableDatabase();
        SelectionBuilder selectionBuilder = new SelectionBuilder();
        int TAG = um.match(uri);
        switch (TAG) {
            case tag2:
                String id = uri.getLastPathSegment();
                selectionBuilder.where("id =?", id);
            case tag1:
                selectionBuilder.table("chentest").where(selection, selectionArgs);
                Cursor cursor = selectionBuilder.query(datasql, projection, sortOrder);
                Context ctx = getContext();
                assert ctx != null;
                cursor.setNotificationUri(ctx.getContentResolver(), uri);
                return cursor;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
    @Override
    public String getType(Uri uri) {
        String type;
        int tag = um.match(uri);
        switch (tag) {
            case tag1:
                type = ContentResolver.CURSOR_DIR_BASE_TYPE + "/test";
                return type;
            case tag2:
                type = ContentResolver.CURSOR_DIR_BASE_TYPE + "/test.chen";
                return type;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = opendb.getWritableDatabase();
        assert db != null;
        final int match = um.match(uri);
        Uri result;
        switch (match) {
            case tag1:
                long id = db.insertOrThrow("chentest", null, values);
                result = Uri.parse("content://" + CHEN_TEST + "/" + id);//指向这条数据的UR
                break;
            case tag2:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return result;

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {


        return 0;
        }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public class openDB extends SQLiteOpenHelper {

        public openDB(Context context) {
            super(context, "chenTestSQLiet", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "create table chentest( _id  INTEGER PRIMARY KEY,name varchar(20) not null,password varchar(60) not null,age varchar(10) not null);";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
