package dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import util.WeatherDBHelper;
import model.Province;

/**
 * Created by 胡正再 on 2017/5/15.
 */

public class ProvinceDao {

        private String table="province";
        private WeatherDBHelper helper;
        private SQLiteDatabase db;

    public ProvinceDao(Context context){
        helper = new WeatherDBHelper(context);
        db =helper.getWritableDatabase();
    }

    public void close(){
        db.close();
        helper.close();
    }
    public long add(String provincename,int provincecode){
        ContentValues values = new ContentValues();
        values.put("provincename",provincename);
        values.put("provincecode",provincecode);
        long id = db.insert(table,null,values);
        close();
        return id;

    }
    //查询所有省份
    public List<Province> query(){
        Cursor cursor = db.rawQuery("select * from province", null);
        Province province = new Province();
        List<Province> list = new ArrayList();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0); //获取第一列的值,第一列的索引从0开始
            String name = cursor.getString(1);//获取第二列的值
            int code = cursor.getInt(2);//获取第三列的值
            province.setId(id);
            province.setProvinceName(name);
            province.setProvinceCode(code);
            list.add(province);
        }
        return list;
    }

}
