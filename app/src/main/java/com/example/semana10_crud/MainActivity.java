package com.example.semana10_crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String idP;
    EditText id, name, lastname;
    Button insert, list, update, delete;
    DatabaseHandler DB;
    String action="new";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id= findViewById(R.id.id);
        name= findViewById(R.id.nombre);
        lastname= findViewById(R.id.apellido);
        insert=findViewById(R.id.btnInsert);
        update= findViewById(R.id.btnUpdate);
        delete=findViewById(R.id.btnDelete);
        list=findViewById(R.id.btnViewData);
        DB = new DatabaseHandler(this);


        showData(); //permitira mostar los datos recibidos del ListActivity

        //evento click de los botones
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT=id.getText().toString();
                String nameTXT= name.getText().toString();
                String lastnameTXT=lastname.getText().toString();

                Boolean checkInsert=DB.insertData(idTXT,nameTXT,lastnameTXT);

                //evaluar si la data ha sido insertada
                if(checkInsert==true){
                    Toast.makeText(MainActivity.this, "Se ha insertado un nuevo registro",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "No se ha podido insertar el registro",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        //boton para mostrar los registros
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result= DB.getData();

                //evaluar si ya existen registros en la tabla
                if(result.getCount()==0){
                    Toast.makeText(MainActivity.this, "Aun no hay registros",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer= new StringBuffer();

                //leer el cursor y almacenar los registros en un StringBuffer
                while(result.moveToNext()){
                    buffer.append("Codigo: " + result.getString(0)+"\n");
                    buffer.append("Nombre: " + result.getString(1)+"\n");
                    buffer.append("Apellido: " + result.getString(2)+"\n\n");
                }

                //mostrar los registros
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Listado de personas registradas");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        //boton actualizar
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT=id.getText().toString();
                String nameTXT= name.getText().toString();
                String lastnameTXT=lastname.getText().toString();

                Boolean checkInsert=DB.updateData(idTXT,nameTXT,lastnameTXT);//llamado al metodo updateData de la clase DatabaseHandler


                //evaluar si la data ha sido actualizado
                if(checkInsert==true){
                    Toast.makeText(MainActivity.this, "Se ha actualizado el registro",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "No se ha podido actualizar el registro",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //boton eliminar
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT=id.getText().toString();

                Boolean checkdeleteData=DB.deleteData(idTXT);

                if(checkdeleteData==true){
                    Toast.makeText(MainActivity.this, "El registro se elimino",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "El registro no se pudo eliminar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showData() {
        try{
            Bundle bundle= getIntent().getExtras();
            action = bundle.getString("action");
            if(action.equals("edit")){

                //mostar/ocultar botones
                update.setVisibility(View.VISIBLE);
                insert.setVisibility(View.GONE);


                idP= bundle.getString("id");
                String person[]=bundle.getStringArray("person");
                TextView tempVal = (TextView) findViewById(R.id.id);
                tempVal.setText(idP);

                tempVal=(TextView) findViewById(R.id.nombre);
                tempVal.setText(person[0].toString());

                tempVal=(TextView) findViewById(R.id.apellido);
                tempVal.setText(person[1].toString());
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Error: " +
                    e.getMessage().toString(), Toast.LENGTH_LONG).show();

        }
    }
}