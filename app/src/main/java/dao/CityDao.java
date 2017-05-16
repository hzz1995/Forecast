package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Province;
import util.WeatherDBHelper;

/**
 * Created by 胡正再 on 2017/5/15.
 */

public class CityDao {
    private String table="city";
    private WeatherDBHelper helper;
    private SQLiteDatabase db;

    public CityDao(Context context){
        helper = new WeatherDBHelper(context);
        db =helper.getWritableDatabase();
    }

    public void close(){
        db.close();
        helper.close();
    }
    public long add(String cityname,int citycode,int provinceid){
        ContentValues values = new ContentValues();
        values.put("cityname",cityname);
        values.put("citycode",citycode);
        values.put("provinceid",provinceid);
        long id = db.insert(table,null,values);
        close();
        return id;

    }

    public List<City> query(){
        Cursor cursor = db.rawQuery("select * from city", null);
        City city = new City();
        List<City> list = new ArrayList();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0); //获取第一列的值,第一列的索引从0开始
            String name = cursor.getString(1);//获取第二列的值
            int code = cursor.getInt(2);//获取第三列的值
            int provinceid = cursor.getInt(3);
            city.setId(id);
            city.setCityName(name);
            city.setCityCode(code);
            city.setProvinceId(provinceid);
            list.add(city);
        }
        return list;
    }
}
