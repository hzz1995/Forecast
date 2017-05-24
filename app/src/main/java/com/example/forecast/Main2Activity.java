package com.example.forecast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gson.Weather;
import util.HttpCallBack;
import util.HttpTool;
import util.JSONParse;
import util.SettingSave;

public class Main2Activity extends AppCompatActivity {

    Context context = this;
    //字符串拼接
    final String path2="http://guolin.tech/api/weather?cityid=";
    final String path3 ="&key=c4aef0d8e66249b6bac8ae0524c15668";
    TextView title;//标题
    TextView tep;//温度
    TextView tq;//天气类型
    TextView title1,fisrt_day1,fisrt_day2,fisrt_day3,fisrt_day4;//小标题
    TextView title2,aqi,pm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        final String weatherid = sp.getString("weatherId","error");
        final JSONParse json = new JSONParse(context);


        if(!sp.getString("weather","error").equals("error")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpTool.getWeatherDataByOkHttp(context, path2, weatherid, path3, Weatherinfo());
                }
            }).start();
        }
        //首次进入weather时设置自定义标题
        final Toolbar tool = (Toolbar)findViewById(R.id.toolbar1);
        title = (TextView) findViewById(R.id.dd);
        tep = (TextView) findViewById(R.id.wendu);
        tq= (TextView)findViewById(R.id.tianqi);
        title1 = (TextView) findViewById(R.id.title1) ;
        fisrt_day1 = (TextView) findViewById(R.id.fir_1) ;
        fisrt_day2 = (TextView) findViewById(R.id.fir_2) ;
        fisrt_day3 = (TextView) findViewById(R.id.fir_3) ;
        fisrt_day4 = (TextView) findViewById(R.id.fir_4) ;

        title2 =(TextView) findViewById(R.id.title2);
        aqi = (TextView)findViewById(R.id.aqi);
        pm= (TextView) findViewById(R.id.pm2_5);




        SharedPreferences gg = context.getSharedPreferences("data",MODE_PRIVATE);
        Weather info = json.parseWeatherResponse(gg.getString("weather","error")).get(0);
        tool.setTitle("");
        title.setText(info.basic.cityName);
        setSupportActionBar(tool);

        tep.setText(info.now.temperature+"°C");
        tq.setText(info.now.more.info);
        title1.setText("预报");
            fisrt_day1.setText(info.forecastList.get(0).date);
            fisrt_day2.setText(info.forecastList.get(0).more.info);
            fisrt_day3.setText(info.forecastList.get(0).temperature.min + "°C");
            fisrt_day4.setText(info.forecastList.get(0).temperature.max + "°C");
        title2.setText("空气质量");
        aqi.setText("AQI："+info.aqi.city.aqi);
        pm.setText("PM2.5："+info.aqi.city.pm25);
    }
    public HttpCallBack Weatherinfo(){
        final JSONParse json = new JSONParse(context);
        HttpCallBack callback = new HttpCallBack() {
            @Override
            public void handleResponse(List list1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
                //上面的代码表示将它所对应的代码在主线程中运行
            }
            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main2Activity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        return callback;
    }

}
