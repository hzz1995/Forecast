package util;

import android.content.SharedPreferences;
import android.os.Debug;
import android.text.TextUtils;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dao.CityDao;
import dao.CountyDao;
import dao.ProvinceDao;
import gson.Weather;
import model.City;
import model.County;
import model.Province;

/**
 * Created by 胡正再 on 2017/5/17.
 */

public class JSONParse {
    private Context context;
    public JSONParse(Context context){
        this.context = context;
    }
    public List<Province> parseProvinceResponse(String response){
        List<Province> list = new ArrayList();
        if(!TextUtils.isEmpty(response)){
            ProvinceDao provinceDao = new ProvinceDao(context);
            try {
                JSONArray allprovince = new JSONArray(response);
                for (int i = 0;i<allprovince.length();i++){
                    JSONObject provinceObject = allprovince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    provinceDao.add(province);
                    list.add(province);
                }

                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    public List<City> parseCityResponse(String response,int ProvinceId){
        List<City> list = new ArrayList();
        if(!TextUtils.isEmpty(response)){
            CityDao cityDao = new CityDao(context);
            try {
                JSONArray allcity = new JSONArray(response);
                for (int i = 0;i<allcity.length();i++){
                    JSONObject cityObject = allcity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(ProvinceId);
                    cityDao.add(city);
                    list.add(city);
                }

                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    public List<County> parseCountyResponse(String response, int CityId){
        List<County> list = new ArrayList();
        if(!TextUtils.isEmpty(response)){
            CountyDao countyDao = new CountyDao(context);
            try {
                JSONArray allcounty = new JSONArray(response);
                for (int i = 0;i<allcounty.length();i++){
                    JSONObject countyObject = allcounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(CityId);
                    countyDao.add(county);
                    list.add(county);
                }

                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    public List<Weather> parseWeatherResponse(String response){
        try {
            SettingSave.saveWeather(context,response);
            List<Weather> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weathercontent = jsonArray.getJSONObject(0).toString();
            Weather weather = new Gson().fromJson(weathercontent,Weather.class);
            list.add(weather);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;

    }

}