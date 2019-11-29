package com.casasolarctpi.prosolar2.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.casasolarctpi.prosolar2.R;
import com.casasolarctpi.prosolar2.fragments.FragmentAyuda;
import com.casasolarctpi.prosolar2.fragments.FragmentConsultas;
import com.casasolarctpi.prosolar2.fragments.FragmentContactanos;
import com.casasolarctpi.prosolar2.fragments.FragmentCorrientes;
import com.casasolarctpi.prosolar2.fragments.FragmentHumedad;
import com.casasolarctpi.prosolar2.fragments.FragmentIncio;
import com.casasolarctpi.prosolar2.fragments.FragmentIrradiancia;
import com.casasolarctpi.prosolar2.fragments.FragmentPerfil;
import com.casasolarctpi.prosolar2.fragments.FragmentVoltajes;
import com.casasolarctpi.prosolar2.models.Constants;
import com.casasolarctpi.prosolar2.models.UserData;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ExpandableListView expListView;
    private ActionBarDrawerToggle mDrawerToggle;
    ExpandableListAdapter listAdapterExpandable;
    List<String> listDataHeader;
    private TextView txtTitle;
    FirebaseAuth mAuth;
    public static UserData userData = new UserData();
    public static String userKey = " ";
    boolean bandera = true;
    public static DatabaseReference reference;
    HashMap<String, List<String>> listDataChild;
    ConstraintLayout clHome, clHumedad, clIrradiancia, clCorriente, clVoltaje, Contactanos, Ayuda, Perfil, CerrarSesion;
    public static Date fechaYHoraAc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fechaYHoraAc = new Date();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (bandera){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new FragmentIncio()).commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.inicio));
            bandera=false;
        }


        navigationView.setNavigationItemSelectedListener(this);

        //METODO PARA CONECTAR ANDROID CON FIREBASE
        inicializarFirebase();
        //GENERACION DE METODOS
        inicializar();
        //ESCUCHA DE BOTONES PARA LA IMPLEMENTACION DEL ONCLICK
        escucharBotones();
    }



    //UTILIZAMOS ESTE METODO PARA IMPLEMENTAR E INICIALIZAR FIREBASE
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(false);}
        catch (Exception e){

        }
            reference = FirebaseDatabase.getInstance().getReference();
    }


    //INICIALIZAMOS LOS ATRIBUTOS DEL MENU PARA QUE SE RECONOZCAN CUANDO SE HAGA CLICK
    private void escucharBotones() {
        clHome.setOnClickListener(this);
        clHumedad.setOnClickListener(this);
        clIrradiancia.setOnClickListener(this);
        clCorriente.setOnClickListener(this);
        clVoltaje.setOnClickListener(this);
        Contactanos.setOnClickListener(this);
        Ayuda.setOnClickListener(this);
        Perfil.setOnClickListener(this);
        CerrarSesion.setOnClickListener(this);

    }

    //METODO PARA INICIALIZAR VALORES
    private void inicializar() {
        //INICIALIZACION DE VALORES
        clHome = findViewById(R.id.cLHome);
        clHumedad = findViewById(R.id.cLHumedad);
        clIrradiancia = findViewById(R.id.cLIrradiancia);
        clCorriente = findViewById(R.id.cLCorriente);
        clVoltaje = findViewById(R.id.cLVoltaje);
        Contactanos = findViewById(R.id.Contactanos);
        Ayuda = findViewById(R.id.ayuda);
        Perfil = findViewById(R.id.perfil);
        CerrarSesion = findViewById(R.id.cerrarsesion);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //OBTIENE EL LISTVIEW
        expListView = (ExpandableListView) findViewById(R.id.expaConsultas);

        //PREPARA LOS DATOS PARA HEADER Y LISTADO EN EXPANDABLELISTVIEW
        prepareListData();

        //CONFIGURACION ADAPTER
        listAdapterExpandable = new com.casasolarctpi.prosolar2.models.ExpandableListAdapter(this, listDataHeader,listDataChild);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent;
                FragmentConsultas.modoGraficar=i1;
                switch (i){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new FragmentConsultas()).commit();
                        switch (i1){
                            case 0:
                                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.titulo_irradiancia_humedad));
                                break;
                            case 1:
                                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.titulo_irradiancia_corriente));
                                break;

                            case 2:
                                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.titulo_irradiancia_voltaje));
                                break;


                            case 3:
                                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.titulo_humedad_temperatura));
                                break;

                        }
                        cerrarDrawable();
                        break;

                    case 1:
                        Uri uri = Uri.parse(Constants.conocenos[i1]);
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        cerrarDrawable();
                        break;
                }
                return false;
            }
        });

        //new ExpandableListAdapter(this, listDataHeader, listDataChild);
        //CONFIGURA ADAPTER EN EXPANDABLELISTVIEW
        expListView.setAdapter(listAdapterExpandable);

        //PUEDES EXPANDIR LOS GRUPOS POR DEFAULT
        int count = listAdapterExpandable.getGroupCount();
        for ( int i = 0; i < count; i++ )
            expListView.expandGroup(i);



    }
    //METODO PARA AGREGAR DATOS A LA LISTA Y MOSTRARLOS EN EL MENU
    private void prepareListData() {
        //CREAMOS EL HEADER Y EL CHILD
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //AGREGAMOS ENCABEZADO
        listDataHeader.add("Consultas");
        listDataHeader.add("Páginas");

        //CREAMOS EL ARREGLO
        List<String> consultas = new ArrayList<String>();
        List<String> paginas = new ArrayList<String>();
        //AGREGAMOS LOS DATOS
        Collections.addAll(paginas, Constants.paginas);
        Collections.addAll(consultas, Constants.consultas);

        //PASAMOS LOS DATOS A QUE SE MUESTREN EN EL MENU
        listDataChild.put(listDataHeader.get(0), consultas);
        listDataChild.put(listDataHeader.get(1), paginas);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // METODO SOBREESCRITO CUANDO SE IMPLEMA EL ONCLICKLISTENER, EL CUAL NOS AYUDA A TENER UN MEJOR CONTROL DE LOS BOTONES Y SUS FUNCIONALIDADES
    @Override
    public void onClick(View v) {
        //CREO UN SWITCH PARA TRABAJAR POR CASOS Y BUSCAR POR ID
        switch (v.getId()){
            case R.id.cLHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new FragmentIncio()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.inicio));
                cerrarDrawable();

                break;

            case R.id.cLHumedad:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new FragmentHumedad()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.dato1));
                cerrarDrawable();
                break;

            case R.id.cLIrradiancia:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new FragmentIrradiancia()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.irradiancia));
                cerrarDrawable();
                break;

            case R.id.cLCorriente:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new FragmentCorrientes()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.voltaje));
                cerrarDrawable();
                break;

            case R.id.cLVoltaje:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new FragmentVoltajes()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.corriente));
                cerrarDrawable();

                break;

            case R.id.Contactanos:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new FragmentContactanos()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.contactanos));
                cerrarDrawable();

                break;

            case R.id.ayuda:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new FragmentAyuda()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.ayuda));
                cerrarDrawable();

                break;

            case R.id.perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new FragmentPerfil()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.perfil));
                cerrarDrawable();
                break;

            case R.id.cerrarsesion:
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();

                break;


        }
    }

    //METODO QUE SE UTILIZA PARA FINALIZAR EL DRAWABLE EN LA VISTA
    private void cerrarDrawable() {

        try {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }catch (Exception ignore){
            Log.e("Error en el drawable", ignore.getMessage());

        }

    }
}
