package com.example.forecast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dao.ProvinceDao;
import model.Province;


public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayAdapter adapter;
    private String[] data={"123","123","123","123","123","123"};
    private List<Province> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
        TextView textview = (TextView)findViewById(R.id.titleposition);

        tool.setTitle("");
        textview.setText("省份");
        setSupportActionBar(tool);




        ProvinceDao dao = new ProvinceDao(this);
        list = dao.query();
        if(list.size()==0){

        }

        else {
            lv = (ListView) findViewById(R.id.listview);
            adapter = new ArrayAdapter<Province>(this, android.R.layout.simple_list_item_activated_1, list);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
                    TextView textview = (TextView) findViewById(R.id.titleposition);
                    tool.setTitle("");
                    textview.setText(list.get(position).getProvinceName());
                    setSupportActionBar(tool);
                    Toast.makeText(MainActivity.this, "clicked this line:" + (position + 1), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
