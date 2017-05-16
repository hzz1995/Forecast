package util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 胡正再 on 2017/5/14.
 */

public class WeatherDBHelper extends SQLiteOpenHelper {
    public WeatherDBHelper(Context context) {
        super(context, "weather.db", null, 1);
    }

        public void onCreate(SQLiteDatabase db){
            db.execSQL("create table if not exists province (" +
                    "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "provincename TEXT,"+
                    "provincecode INTERGER"+
                    ")");
            db.execSQL("create table if not exists city"+
            "(id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cityename TEXT,"+
                    "citycode INTERGER"+
                    ")");
            db.execSQL("create table if not exists county"+
                    "(id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "countyname TEXT,"+
                    "weatherid TEXT,"+
                    "cityid INTERGER"+
                    ")");


    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("alter table person add account varchar(20)");
    }
}
