package lab2activities.gr7.compumovil.udea.edu.co.lab2activities.lab2Activities;
/**
 * Created by dfrancisco.hernandez on 3/09/16.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import lab2activities.gr7.compumovil.udea.edu.co.lab2activities.R;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //declaracion de variables globes
    DbHelper dbHelper;
    SQLiteDatabase db;
    Fragment about = new AcercadeFragment();
    Fragment places = new LugarList();
    AgregarLugarFragment add = new AgregarLugarFragment();
    Fragment info = new InformacionFragment();
    FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
    FloatingActionButton fab;
    ///////////////

    private boolean controlSelect=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        dbHelper =new DbHelper(this);//nueva base de datos
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        manager.replace(R.id.fragment_container, about);
        manager.commit();
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        manager = getSupportFragmentManager().beginTransaction();
        places = new LugarList();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!controlSelect){moveTaskToBack(true);}
            else{
                manager.replace(R.id.fragment_container, places);
                manager.commit();
                controlSelect=false;
                fab.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//opcion cerrar sesion y guarda datos en la BD
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            db = dbHelper.getWritableDatabase();
            db.execSQL("delete from " + StatusContract.TABLE_LOGIN);
            db.close();
            Intent newActivity = new Intent(this, LoginActivity.class);
            startActivity(newActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        manager = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();
        if (id == R.id.profile) {
            info = new InformacionFragment();
            manager.replace(R.id.fragment_container,info);
            fab.setVisibility(View.INVISIBLE);
        } else if (id == R.id.races) {
            places = new LugarList();
            manager.replace(R.id.fragment_container, places);
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.about) {
            about = new AcercadeFragment();
            manager.replace(R.id.fragment_container, about);
            fab.setVisibility(View.INVISIBLE);
        }else if (id == R.id.action_settings) {
            db = dbHelper.getWritableDatabase();
            db.execSQL("delete from " + StatusContract.TABLE_LOGIN);
            db.close();
            Intent newActivity = new Intent(this, LoginActivity.class);
            startActivity(newActivity);
            return true;
        }
        manager.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void AgregarLugar(View v){
        manager = getSupportFragmentManager().beginTransaction();
        add = new AgregarLugarFragment();
        manager.replace(R.id.fragment_container, add);
        manager.commit();
        fab.setVisibility(View.INVISIBLE);
        controlSelect=true;
    }

    public void GClic(View v){
        add.ClickGalleryR();
    }
    public void CClic(View v){
        add.ClickCameraR();
    }
    public void OtroLugar(View v){
        add.ValidarPlaces();
        manager = getSupportFragmentManager().beginTransaction();
        places = new LugarList();
        manager.replace(R.id.fragment_container, places);
        manager.commit();
        fab.setVisibility(View.VISIBLE);
    }
}
