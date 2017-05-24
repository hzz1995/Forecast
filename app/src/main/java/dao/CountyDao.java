package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.County;
import util.WeatherDBHelper;

/**
 * Created by 胡正再 on 2017/5/15.
 */

public class CountyDao {
    private String table="county";
    private WeatherDBHelper helper;
    private SQLiteDatabase db;

    public CountyDao(Context context){
        helper = new WeatherDBHelper(context);
        db =helper.getWritableDatabase();
    }

    public void close(){
        /*db.close();
        helper.close();*/
    }
    public long add(County county){
        ContentValues values = new ContentValues();
        values.put("countyname",county.getCountyName());
        values.put("weatherid",county.getWeatherId());
        values.put("cityid",county.getCityId());
        long id = db.insert(table,null,values);
        close();
        return id;

    }

    public List<County> query(){
        Cursor cursor = db.rawQuery("select * from county", null);

        List<County> list = new ArrayList();
        while (cursor.moveToNext()) {
            County county = new County();
            int id = cursor.getInt(0); //获取第一列的值,第一列的索引从0开始
            String name = cursor.getString(1);//获取第二列的值
            String weatherid = cursor.getString(2);//获取第三列的值
            int provinceid = cursor.getInt(3);
            county.setId(id);
            county.setCountyName(name);
            county.setWeatherId(weatherid);
            county.setCityId(provinceid);
            list.add(county);
        }
        return list;
    }

    public List<County> queryOne(int citycode){
        Cursor cursor = db.rawQuery("select * from county where cityid=?", new String[]{String.valueOf(citycode)});

        List<County> list = new ArrayList();
        while (cursor.moveToNext()) {
            County county = new County();
            int id = cursor.getInt(0); //获取第一列的值,第一列的索引从0开始
            String name = cursor.getString(1);//获取第二列的值
            String weatherid = cursor.getString(2);//获取第三列的值
            int provinceid = cursor.getInt(3);
            county.setId(id);
            county.setCountyName(name);
            county.setWeatherId(weatherid);
            county.setCityId(provinceid);
            list.add(county);
        }
        return list;
    }
}
