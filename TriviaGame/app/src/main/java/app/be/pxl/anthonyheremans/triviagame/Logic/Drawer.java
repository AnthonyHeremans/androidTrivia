package app.be.pxl.anthonyheremans.triviagame.Logic;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.be.pxl.anthonyheremans.triviagame.R;

/**
 * Created by 11401671 on 10/11/2017.
 */

public class Drawer {
    ArrayAdapter<String> adapter;

    public void addDrawerItems(Context c, ListView list) {

        List<String> menuArray = new ArrayList<>();
        menuArray.add("Home");
        menuArray.add("New Game");
        menuArray.add("Shop");
        menuArray.add("Logout");

        adapter = new ArrayAdapter<String>(c, R.layout.menu_item, menuArray);
        list.setAdapter(adapter);
    }
}
