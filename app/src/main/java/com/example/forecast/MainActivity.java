package com.example.forecast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dao.CityDao;
import dao.CountyDao;
import dao.ProvinceDao;
import gson.Weather;
import model.City;
import model.County;
import model.Province;
import util.HttpCallBack;
import util.HttpTool;
import util.SettingSave;
import util.WeatherDBHelper;


public class MainActivity extends AppCompatActivity{

    private Context context=this;
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Province> list;
    private List<City> list1;
    private List<County> list2;
    final String path="http://guolin.tech/api/china";
    final String path1="http://guolin.tech/api/china/";

    //设置层级。
    private int Level1=1;
    private int Level2=2;
    private int Level3=3;
   // int index=0;//设置点击进入哪个省、市。

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //WeatherDBHelper.onDelete(this);

        SharedPreferences sp = context.getSharedPreferences("data", MODE_PRIVATE);
        if (!sp.getString("weather", "error").equals("error") && sp.getInt("level", -1) != 4) {
            Bundle bundle = SettingSave.getSelect(context);
            Intent intent = new Intent();
            intent.setClassName("com.example.forecast", "com.example.forecast.Main2Activity");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (sp.getInt("level", -1) == 4) {
            int cityid = sp.getInt("cityId", -1);
            CityDao dao1 = new CityDao(this);
            list1 = dao1.queryOnebyCitycode(cityid);
            final Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
            SettingSave.saveSelectLevel(context,Level3);
            TextView textview = (TextView) findViewById(R.id.titleposition);
            tool.setTitle("");
            textview.setText(list1.get(0).getCityName());
            setSupportActionBar(tool);


            CountyDao dao2 = new CountyDao(this);
            list2 = dao2.queryOne(cityid);
            final String[] citylist = new String[list2.size()];
            for (int i = 0; i < list2.size(); i++)
                citylist[i] = list2.get(i).getCountyName();
            ListView(citylist);

        } else {

            //首次进入app时设置自定义标题
            final Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
            TextView textview = (TextView) findViewById(R.id.titleposition);
            tool.setTitle("");
            textview.setText("省份");
            setSupportActionBar(tool);
            SettingSave.saveSelectLevel(context, Level1);
            //判断数据库是否有数据。有的话，转成字符串数组，没有则从网络获取
            //创建省、市、镇的dao操作。
            ProvinceDao dao = new ProvinceDao(this);
            //  dao.DelUserInfo();  //测试用//

            list = dao.query();

            if (list.size() == 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpTool.getDataByOkHttp(context, path, SetProvince());
                    }
                }).start();
                //上面的代码表示通知CPU开辟新的线程，CPU自己觉得合适时候运行该线程对应的代码，
                //并不等待执行完新线程代码才执行下一行
            } else {
                //数据库中有数据。
                final String[] list3 = new String[list.size()];
                for (int i = 0; i < list.size(); i++) list3[i] = list.get(i).getProvinceName();
                ListView(list3);
            }

        }
    }
    //获取选中省的市
    public void Create1(final int index){



        CityDao dao1 = new CityDao(this);
        //查询城市
        list1=dao1.queryOne(index);
        SettingSave.saveSelectProvince(context,index);
        SettingSave.saveSelectLevel(context,Level2);
        //将城市转换成数组
        final String[] citylist = new String[list1.size()];
        for(int i = 0;i<list1.size();i++)
            citylist[i]= list1.get(i).getCityName();
        if(list1.size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpTool.getCityDataByOkHttp(context,path1,index,SetCity());
                }
            }).start();

        }
        else{
            ListView(citylist);
        }
    }
//获取县级市及weatherid
    public void Create2(final int index){
        SharedPreferences sp = context.getSharedPreferences("data",MODE_PRIVATE);
        CityDao dao = new CityDao(this);

       final int index1=dao.queryOne(sp.getInt("provinceId",-1)).get(index-1).getCityCode();

       SettingSave.saveSelectCity(context,index1);
        SettingSave.saveSelectLevel(context,Level3);
        CountyDao dao2 = new CountyDao(this);
        //查询县级市
        list2=dao2.queryOne(index1);
        //将县级市转换成数组
        final String[] countylist = new String[list2.size()];
        for(int i = 0;i<list2.size();i++)
            countylist[i]= list2.get(i).getCountyName();
        if(list2.size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpTool.getCountyDataByOkHttp(context,path1,index,index1,SetCounty());
                }
            }).start();

        }
        else{
            ListView(countylist);
        }
    }

    //将所有数据显示在LISTviEW中
public  void ListView(final String[] list3){

    //将所有数据适配到listview中
    lv = (ListView) findViewById(R.id.listview);
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, list3);
    lv.setAdapter(adapter);

    //点击触发按钮
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            SharedPreferences sp = context.getSharedPreferences("data",MODE_PRIVATE);
            int layout = sp.getInt("level",-1);
            //再次设置自定义标题栏
            if(layout!=3) {
                Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
                TextView textview = (TextView) findViewById(R.id.titleposition);
                tool.setTitle("");
                textview.setText(list3[position]);
                setSupportActionBar(tool);
            }
            int index =position+1;



            switch (layout){
                case 1:Create1(index);break;
                case 2:Create2(index);break;
                case 3:Weather(index);break;
                //default:Create2(index);break;
            }


            Toast.makeText(MainActivity.this, "clicked this line:" + (position + 1), Toast.LENGTH_SHORT).show();
        }
    });
}

    public HttpCallBack SetProvince(){
        HttpCallBack callback = new HttpCallBack() {
            @Override
            public void handleResponse(List list1) {
                final List<Province> list = list1;
                final String[] list3 = new String[list.size()];
                for(int i = 0;i<list.size();i++)
                    list3[i]= list.get(i).getProvinceName();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView(list3);
                    }
                });
                //上面的代码表示将它所对应的代码在主线程中运行
            }
            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        return callback;
    }

    public HttpCallBack SetCity(){
        HttpCallBack callback = new HttpCallBack() {
            @Override
            public void handleResponse(List list1) {
                final List<City> list = list1;
                final String[] list3 = new String[list.size()];
                for(int i = 0;i<list.size();i++)
                    list3[i]= list.get(i).getCityName();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView(list3);
                    }
                });
                //上面的代码表示将它所对应的代码在主线程中运行
            }
            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        return callback;
    }




    public HttpCallBack SetCounty(){
        HttpCallBack callback = new HttpCallBack() {
            @Override
            public void handleResponse(List list1) {
                final List<County> list = list1;
                final String[] list3 = new String[list.size()];
                for(int i = 0;i<list.size();i++)
                    list3[i]= list.get(i).getCountyName();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView(list3);
                    }
                });
                //上面的代码表示将它所对应的代码在主线程中运行
            }
            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        return callback;
    }

    public void Weather(int index) {
        CountyDao dao = new CountyDao(context);
        SharedPreferences sp = context.getSharedPreferences("data",MODE_PRIVATE);
        final String weatherid = dao.queryOne(sp.getInt("cityId",-1)).get(index-1).getWeatherId();
        SettingSave.saveSelectWeatherId(context,weatherid);

        Bundle bundle = SettingSave.getSelect(context);
        Intent intent = new Intent();
        intent.setClassName("com.example.forecast","com.example.forecast.Main2Activity");
        intent.putExtras(bundle);
        startActivity(intent);


    }


   public void back(View view){

       int layout = getSharedPreferences("data",MODE_PRIVATE).getInt("level",-1);
       int provinceid = getSharedPreferences("data",MODE_PRIVATE).getInt("provinceId",-1);
       int cityid = getSharedPreferences("data",MODE_PRIVATE).getInt("cityId",-1);
        if(layout==1){
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else if(layout==2){

            //返回省份
            final Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
            TextView textview = (TextView)findViewById(R.id.titleposition);
            tool.setTitle("");
            textview.setText("省份");
            setSupportActionBar(tool);
            ProvinceDao dao = new ProvinceDao(this);
            list = dao.query();
            SettingSave.saveSelectLevel(context,Level1);
            final String[] list3 = new String[list.size()];
            for(int i = 0;i<list.size();i++)
            list3[i]= list.get(i).getProvinceName();
            ListView(list3);
        }
        else{
            ProvinceDao dao1 = new ProvinceDao(this);
            List<Province> list = new ArrayList<>();
            //查询城市
            list=dao1.queryOneBycode(provinceid);
            final Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
            TextView textview = (TextView)findViewById(R.id.titleposition);
            tool.setTitle("");
            textview.setText(list.get(0).getProvinceName());
            setSupportActionBar(tool);
            CityDao dao = new CityDao(this);
            List<City> list1 = new ArrayList<>();
            list1= dao.queryOne(list.get(0).getProvinceCode());
            SettingSave.saveSelectLevel(context,Level2);
            //将城市转换成数组
            final String[] citylist = new String[list1.size()];
            for(int i = 0;i<list1.size();i++)
            citylist[i]= list1.get(i).getCityName();
            ListView(citylist);
        }

    }
}
