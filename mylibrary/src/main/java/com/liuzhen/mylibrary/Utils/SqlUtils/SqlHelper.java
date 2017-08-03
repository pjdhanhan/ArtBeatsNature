package com.liuzhen.mylibrary.Utils.SqlUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.liuzhen.mylibrary.Base.LibApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * 数据访问对象,借助此对象中的方法,访问SQLite数据库
 */
public class SqlHelper {

    private Context context;
    private BrowsingHistoryDBHelper dbHelper;
    private String TABLE;

    /**
     * 当构建GoodsCollectionDao对象时可以构建一个dbHelper对象
     * 可以借助dbHelper对象执行一些数据库操作(借助SQLiteOpenDatabase)
     */
    public SqlHelper(Context context, int versionCode, String TABLE) {
        this.context = context;
        this.TABLE = TABLE;
        dbHelper = new BrowsingHistoryDBHelper(context, TABLE + ".db", null, versionCode);
    }


    public long saveUserInfor(SqlBean bean) {
        ContentValues values = new ContentValues();
        String[] SQL_date = bean.toString().split(",");
        for (int i = 0; i < SQL_date.length; i++) {
            try {
                values.put(SQL_date[i].split("=")[0].trim(), SQL_date[i].split("=")[1].trim());
            } catch (Exception e) {
                values.put(SQL_date[i].split("=")[0].trim(), "");
            }
        }
        values.put("Date", new Date().getTime() + "");
        return insert(values);
    }


    /**
     * 功能：数据数据的写入操作
     *
     * @param values 封装要写入到数据库的数据
     * @return 表示row id,假如返回为-1表示写入失败
     */
    public long insert(ContentValues values) {
        //1.获得SQLiteDatabase对象(数据库不存在则创建(会调用dbHelper中的onCreate))
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        //2.执行写入操作
        long id = sdb.insert(TABLE, null, values);
        //3.释放资源
        sdb.close();
        return id;
    }

    public Cursor query() {
        String sql = "select * from " + TABLE + " order by Date desc";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(sql, null);
    }


    public void delete() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();//获取数据库对象
        //删除hero_info表中所有的数据 传入1 表示删除所有行------>点击back按钮
        db.delete(TABLE, null, null);
    }

    //根据id更新记录
    public void update(String key, String value) {
        ContentValues values = new ContentValues();
        values.put(key, value);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        sdb.update(TABLE, values, null, null);
        sdb.close();
    }

    public int SelectUserBean() {
        Cursor cursor = query();
        if (cursor == null || cursor.getCount() == 0) {
            return 0;
        } else {
            while (cursor.moveToNext()) {
                JSONObject object = new JSONObject();
                SqlBean obj = new SqlBean();
                String[] obj_data = obj.toString().split(",");
                for (int i = 0; i < obj_data.length; i++) {
                    try {
                        object.put(obj_data[i].split("=")[0].trim(), cursor.getString(cursor.getColumnIndex(obj_data[i].split("=")[0].trim())));
                    } catch (JSONException e) {
                        try {
                            object.put(obj_data[i].split("=")[0].trim(), "");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
                obj = new Gson().fromJson(object.toString(), SqlBean.class);
                LibApp.setUserBean(obj);
            }
            //关闭Cursor对象(释放资源)
            cursor.close();
        }
        return cursor.getCount();
    }

    class BrowsingHistoryDBHelper extends SQLiteOpenHelper {

        public BrowsingHistoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);

        }

        public boolean deleteDatabase(Context context) {
            return context.deleteDatabase(TABLE + ".db");


        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            SqlBean bean = new SqlBean();
            String[] sqlTable = bean.toString().split(",");
            String sql = "create table " + TABLE + "(sql_id integer primary key autoincrement,";
            for (int i = 0; i < sqlTable.length; i++) {
                sql = sql + sqlTable[i].split("=")[0].trim() + "  verchar(100),";
            }
            sql = sql + "Date" + "  verchar(100))";
            db.execSQL(sql);
        }

        /**
         * 此方法在数据库版本升级时会自动执行，具体在此方法中做什么，由业务而定。
         * 一般可以在此方法中执行备份或者删除表，然后重建。
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String sql = "drop table if exists " + TABLE;
            db.execSQL(sql);
            onCreate(db);
        }
    }
}
