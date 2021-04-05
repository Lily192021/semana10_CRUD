package com.example.semana10_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    DatabaseHandler DB;
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //mostrar datos en el ListActivity
        showData();
    }

    private void showData() {
        DB = new DatabaseHandler(ListActivity.this);
        c= DB.getData();

        //evaluar si existen registros en la tabla
        if(c.moveToFirst()){
            ListView listData = (ListView) findViewById(R.id.listData);

            //crear un array list
            final ArrayList<String> allData = new ArrayList<String>();

            //crear un aarray adapter
            final ArrayAdapter<String> aData = new ArrayAdapter<String>(ListActivity.this,
                    android.R.layout.simple_expandable_list_item_1, allData);

            //agregar el adaptador al listview
            listData.setAdapter(aData);

            //mostrar los registros
            do{
                allData.add(c.getString(1));
            }while (c.moveToNext());
        }else{
            Toast.makeText(ListActivity.this, "No hay registros para mostrar",
                    Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void addData(View v){
        Intent add = new Intent(ListActivity.this, MainActivity.class);
        startActivity(add);
    }
}