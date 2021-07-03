package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
        implements ProfileFragment.Controller, ProfileListFragment.Controller {
    private static final String TAG = "@@@ AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new ProfileListFragment())
                .commit();
    }

    @Override
    public void saveResult(DossierEntity dossier) {
        //todo
    }

    @Override
    public void openProfileScreen(DossierEntity dossier) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                .add(isLandscape ? R.id.detail_container : R.id.container, ProfileFragment.newInstance(dossier))
                .addToBackStack(null)
                .commit();
    }
}