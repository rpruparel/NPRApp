package com.example.hw4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Gallery;

public class BaseActivity extends Activity {

    @Override
    
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        menu.setGroupEnabled(R.id.menuGroup, true);
        inflater.inflate(R.menu.menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        if (item.getItemId() == R.id.clear) {
            FavoritesUtil.initFavoritesUtil(BaseActivity.this, StoriesActivity.PREF_FAVORITES, MODE_PRIVATE);
            FavoritesUtil.clearFavorites();
        }


        return super.onOptionsItemSelected(item);
    }




}