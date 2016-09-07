package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.androidprojects.projetfiesta.demarrage.ActiviteNonLogue;

public class Apropos extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void openBrowser(View view){

        //Get url from tag
        String url = (String)view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        //pass the url to intent data
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        toolbar.setTitle(R.string.title);
        toolbar.setTitleTextColor(Color.WHITE);

        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem info = menu.findItem(R.id.action_info);

        SharedPreferences settings = getSharedPreferences("prefs",0);
        SharedPreferences.Editor editor = settings.edit();
        boolean estLogue = settings.getBoolean("estLogue",false);

        if(!estLogue)
            logout.setVisible(false);

        info.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id == R.id.action_info) {
            Intent intent = new Intent(this, Apropos.class);
            startActivity(intent);
        }

        if(res_id == R.id.action_logout) {

            SharedPreferences settings = getSharedPreferences("prefs",0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("estLogue",false);
            editor.putLong("idUtilisateur", 0);
            editor.commit();
            Intent i = new Intent(this, ActiviteNonLogue.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
