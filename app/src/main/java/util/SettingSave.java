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
        bundle.putInt("selectLevel",sp.getInt("selectLevel",-1));
        bundle.putInt("provinceId",sp.getInt("provinceId",-1));
        bundle.putInt("cityId",sp.getInt("cityId",-1));
        bundle.putInt("county",sp.getInt("countyId",-1));
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
    public static boolean saveSelectCounty(Context context,int countyId){
        SharedPreferences sp = context.getSharedPreferences("data",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("countyId",countyId);
        editor.commit();
        return true;
    }

}
