package com.example.administrator.a7stuff.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.administrator.a7stuff.Object.Stuff;
import com.example.administrator.a7stuff.R;
import com.example.administrator.a7stuff.fragment.StuffDetails1_Edit_Fragment;
import com.example.administrator.a7stuff.fragment.StuffDetails1_Fragment;

public class StuffDetailsActivity extends AppCompatActivity  implements StuffDetails1_Edit_Fragment.Callbacks {
    Stuff stuff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_details);

         stuff = getIntent().getParcelableExtra("Stuff");

        initInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentContainer,StuffDetails1_Fragment.newInstance(stuff))
                .commit();
    }

    private void initInstance() {
        //create home button
        getSupportActionBar().setHomeButtonEnabled(true);
        //Change home button to Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //get up button then handle event in onOptionsItemSelected with specific ID android.R.id.home

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClicked(Stuff updatedStuff) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, StuffDetails1_Fragment.newInstance(updatedStuff))
                .commit();
    }


}
