package com.example.forecast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.List;

import gson.Weather;
import util.HttpCallBack;
import util.HttpTool;
import util.JSONParse;
import util.SettingSave;

public class Main2Activity extends AppCompatActivity {

    Context context = this;
    SwipeRefreshLayout swipeRefreshLayout;

    //字符串拼接
    final String path2="http://guolin.tech/api/weather?cityid=";
    final String path3 ="&key=c4aef0d8e66249b6bac8ae0524c15668";
    TextView title;//标题
    TextView tep;//温度
    TextView tq;//天气类型
    TextView title1,fisrt_day1,fisrt_day2,fisrt_day3,fisrt_day4;//小标题
    TextView title2,aqi,pm;
    TextView title3,ssd,xc,cx;//舒适度，洗车指数，运行建议
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        final JSONParse json = new JSONParse(context);

        SharedPreferences sp = context.getSharedPreferences("data",MODE_PRIVATE);
        //SettingSave.saveSelectLevel(context,-1);//清空层
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        final String weatherid = sp.getString("weatherId","error");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpTool.getWeatherDataByOkHttp(context, path2, weatherid, path3, Weatherinfo());
                }
            }).start();

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    HttpTool.getWeatherDataByOkHttp(context, path2, weatherid, path3, Weatherinfo());
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

        }


    public HttpCallBack Weatherinfo(){
        final JSONParse json = new JSONParse(context);
        HttpCallBack callback = new HttpCallBack() {
            @Override
            public void handleResponse(List list1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //首次进入weather时设置自定义标题
                        final Toolbar tool = (Toolbar) findViewById(R.id.toolbar1);


                        title = (TextView) findViewById(R.id.dd);
                        tep = (TextView) findViewById(R.id.wendu);
                        tq = (TextView) findViewById(R.id.tianqi);
                        title1 = (TextView) findViewById(R.id.title1);
                        fisrt_day1 = (TextView) findViewById(R.id.fir_1);
                        fisrt_day2 = (TextView) findViewById(R.id.fir_2);
                        fisrt_day3 = (TextView) findViewById(R.id.fir_3);
                        fisrt_day4 = (TextView) findViewById(R.id.fir_4);

                        title2 = (TextView) findViewById(R.id.title2);
                        aqi = (TextView) findViewById(R.id.aqi);
                        pm = (TextView) findViewById(R.id.pm2_5);

                        title3 = (TextView) findViewById(R.id.title3);
                        ssd = (TextView) findViewById(R.id.ssd);
                        xc = (TextView) findViewById(R.id.xc);
                        cx = (TextView) findViewById(R.id.cx);


                        SharedPreferences gg = context.getSharedPreferences("data", MODE_PRIVATE);
                        Weather info = json.parseWeatherResponse(gg.getString("weather", "error")).get(0);
                        tool.setTitle("");
                        title.setText(info.basic.cityName);
                        setSupportActionBar(tool);

                        tep.setText(info.now.temperature + "°C");
                        tq.setText(info.now.more.info);
                        title1.setText("预报");
                        fisrt_day1.setText(info.forecastList.get(0).date);
                        fisrt_day2.setText(info.forecastList.get(0).more.info);
                        fisrt_day3.setText(info.forecastList.get(0).temperature.min + "°C");
                        fisrt_day4.setText(info.forecastList.get(0).temperature.max + "°C");
                        title2.setText("空气质量");
                        if(info.aqi==null){
                            aqi.setText("AQI：无" );
                            pm.setText("PM2.5：无" );
                        }
                        else
                        {
                            aqi.setText("AQI：" + info.aqi.city.aqi);
                            pm.setText("PM2.5：" + info.aqi.city.pm25);
                        }

                        title3.setText("生活建议");
                        ssd.setText("舒适度：" + info.suggestion.comfort.info);
                        xc.setText("洗车指数：" + info.suggestion.carWash.info);
                        cx.setText("运行建议：" + info.suggestion.sport.info);

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
    public void selectCounty(View view){

        SettingSave.saveSelectLevel(context,4);
        Intent intent = new Intent();
        Bundle bundle = SettingSave.getSelect(context);
        intent.setClassName("com.example.forecast","com.example.forecast.MainActivity");
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
