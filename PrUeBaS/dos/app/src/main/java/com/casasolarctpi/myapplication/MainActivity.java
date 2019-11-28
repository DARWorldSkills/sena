package com.casasolarctpi.myapplication;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ExpandableListView expListView;
    private ActionBarDrawerToggle mDrawerToggle;
    ExpandableListAdapter listAdapterExpandable;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // obtiene el listview.
       // expListView = (ExpandableListView) findViewById(R.id.expandable_list);
        // prepara datos para Header y Listado en ExpandableListView.
      //  prepareListData();
        // configura Adapter.
       // listAdapterExpandable = new com.casasolarctpi.myapplication.ExpandableListAdapter(this, listDataHeader,listDataChild);
                //new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // configura Adapter en ExpandableListView.
//        expListView.setAdapter(listAdapterExpandable);
        // Puedes expandir los grupos por default.
        //int count = listAdapterExpandable.getGroupCount();
        //for ( int i = 0; i < count; i++ )
          //  expListView.expandGroup(i);


    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Agrega Encabezados.
        listDataHeader.add("Lenguajes de Programación");
        //listDataHeader.add("Tipos");
        //listDataHeader.add("Paises");

        // Agrega datos.
        List<String> lenguajes = new ArrayList<String>();
        lenguajes.add("C++");
        lenguajes.add("Java");
        lenguajes.add("Ruby");
        lenguajes.add("Python");
        lenguajes.add("Swift");
        lenguajes.add("Objective C");
        lenguajes.add("C#");

        // Agrega datos.
        List<String> tipos = new ArrayList<String>();
        tipos.add("Desarrollo Mobil");
        tipos.add("Escritorio");
        tipos.add("Web");
        tipos.add("Juegos");
        tipos.add("Bases de Datos");
        tipos.add("Analisis de Datos");

        // Agrega datos.
        List<String> paises = new ArrayList<String>();
        paises.add("Rumania");
        paises.add("Ucrania");
        paises.add("México");
        paises.add("Grecia");
        paises.add("Holanda");
        paises.add("El Salvador");
        paises.add("Guatemala");
        paises.add("Canada");
        paises.add("Francia");

        listDataChild.put(listDataHeader.get(0), lenguajes);
        //listDataChild.put(listDataHeader.get(1), tipos);
        //listDataChild.put(listDataHeader.get(2), paises);

//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
  //          @Override
    //        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
      //          ConsultasFr.modoGraficar = (int) id;
        //        switch ((int) id){
          //          case 0:
            //            Toast.makeText(MainActivity.this, "gasd", Toast.LENGTH_SHORT).show();
              //          cerrar();
                //        break;

                    //case 1:

                      //  Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                        //break;

                //}
                //return false;


            //}
        //});
    }

    private void cerrar() {

        try {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }catch (Exception ignored){
            Log.e("Error en drawer",ignored.getMessage());
        }
    }
}
