package util;



import android.content.Context;
import android.content.SharedPreferences;

import com.example.forecast.MainActivity;

import java.util.ArrayList;
import java.util.List;

import gson.Weather;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 胡正再 on 2017/5/17.
 */

public class HttpTool {

    //上面的代码表示通知CPU开辟新的线程，CPU自己觉得合适时候运行该线程对应的代码，
    //并不等待执行完新线程代码才执行下一行
    public static void getDataByOkHttp(Context context,String url, final HttpCallBack httpCallBack){

        OkHttpClient client=new OkHttpClient();
        final JSONParse json = new JSONParse(context);
        //下面的代码用的是建造者设计模式,连缀
        Request request=new Request.Builder().get().url(url).build();
        //下面代码表示生成一个访问动作,将客户端（接收源）和请求对象关联起来
        final Call call=client.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    下面代码表示执行请求动作，返回答应包
                    Response response=call.execute();
                    //该处调用主线程的成功方法
                    if(response.code()==200){
                        httpCallBack.handleResponse(json.parseProvinceResponse(response.body().string()));
                    }else {
                        httpCallBack.onFailure();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //该处可以调用主线程的失败方法
                    httpCallBack.onFailure();
                }
            }
        }).start();
    }

    public static void getCityDataByOkHttp(Context context, String url, final int provinceid, final HttpCallBack httpCallBack){

        OkHttpClient client=new OkHttpClient();
        final JSONParse json = new JSONParse(context);
        url=url+provinceid;
        //下面的代码用的是建造者设计模式,连缀
        Request request=new Request.Builder().get().url(url).build();
        //下面代码表示生成一个访问动作,将客户端（接收源）和请求对象关联起来
        final Call call=client.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    下面代码表示执行请求动作，返回答应包
                    Response response=call.execute();
                    //该处调用主线程的成功方法
                    if(response.code()==200){
                        httpCallBack.handleResponse(json.parseCityResponse(response.body().string(),provinceid));
                    }else {
                        httpCallBack.onFailure();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //该处可以调用主线程的失败方法
                    httpCallBack.onFailure();
                }
            }
        }).start();
    }

    public static void getCountyDataByOkHttp(Context context, String url, int provinceid,final int cityid, final HttpCallBack httpCallBack){

        OkHttpClient client=new OkHttpClient();
        final JSONParse json = new JSONParse(context);
        url=url+provinceid+"/"+cityid;
        //下面的代码用的是建造者设计模式,连缀
        Request request=new Request.Builder().get().url(url).build();
        //下面代码表示生成一个访问动作,将客户端（接收源）和请求对象关联起来
        final Call call=client.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    下面代码表示执行请求动作，返回答应包
                    Response response=call.execute();
                    //该处调用主线程的成功方法
                    if(response.code()==200){
                        httpCallBack.handleResponse(json.parseCountyResponse(response.body().string(),cityid));
                    }else {
                        httpCallBack.onFailure();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //该处可以调用主线程的失败方法
                    httpCallBack.onFailure();
                }
            }
        }).start();
    }

    public static void getWeatherDataByOkHttp(final Context context, String url, String weatherid, String url1, final HttpCallBack httpCallBack){

        OkHttpClient client=new OkHttpClient();
        final JSONParse json = new JSONParse(context);
        url=url+weatherid+url1;
        //下面的代码用的是建造者设计模式,连缀
        Request request=new Request.Builder().get().url(url).build();
        //下面代码表示生成一个访问动作,将客户端（接收源）和请求对象关联起来
        final Call call=client.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    下面代码表示执行请求动作，返回答应包
                    Response response=call.execute();
                    //该处调用主线程的成功方法
                    if(response.code()==200){
                        httpCallBack.handleResponse(json.parseWeatherResponse(response.body().string()));
                    }else {
                        httpCallBack.onFailure();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //该处可以调用主线程的失败方法
                    httpCallBack.onFailure();
                }
            }
        }).start();
    }

}
