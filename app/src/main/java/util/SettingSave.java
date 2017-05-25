package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by 胡正再 on 2017/5/15.
 */

public class SettingSave {
    public static Bundle getSelect(Context context){
        SharedPreferences sp = context.getSharedPreferences("data",context.MODE_PRIVATE);
        Bundle bundle = new Bundle();
        bundle.putInt("level",sp.getInt("level",-1));
        bundle.putInt("provinceId",sp.getInt("provinceId",-1));
        bundle.putInt("cityId",sp.getInt("cityId",-1));
        bundle.putString("weatherId",sp.getString("weatherId","error"));
        bundle.putString("weather",sp.getString("weather","error"));
        return bundle;
    }

    public static boolean saveWeather(Context context,String weather){
        SharedPreferences sp = context.getSharedPreferences("data",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("weather",weather);
        editor.commit();
        return true;
    }

    public static boolean saveSelectProvince(Context context,int provinceId){
        SharedPreferences sp = context.getSharedPreferences("data",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("provinceId",provinceId);
        editor.commit();
        return true;
    }

    public static boolean saveSelectCity(Context context,int cityId){
        SharedPreferences sp = context.getSharedPreferences("data",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("cityId",cityId);
        editor.commit();
        return true;
    }
    public static boolean saveSelectWeatherId(Context context,String weatherId){
        SharedPreferences sp = context.getSharedPreferences("data",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("weatherId",weatherId);
        editor.commit();
        return true;
    }

    public static boolean saveSelectLevel(Context context,int level){
        SharedPreferences sp = context.getSharedPreferences("data",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("level",level);
        editor.commit();
        return true;
    }


}
