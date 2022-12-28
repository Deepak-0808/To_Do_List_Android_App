package com.example.mytask;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class homePage extends AppCompatActivity {

    BottomNavigationView bottomNavBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar=findViewById(R.id.toolbar);
        bottomNavBar=findViewById(R.id.bottomNavBar);






        bottomNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.taskNav)
                {
                    loadFrag(new AFragment(),true);
                }
                else if (id==R.id.calendarNav)
                {
                    loadFrag(new BFragment(),false);
                }
                else if (id==R.id.settingNav)
                {
                    loadFrag(new CFragment(),false);

                }
                return true;
            }
        });

        bottomNavBar.setSelectedItemId(R.id.taskNav);

    }



    public void loadFrag(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag)
            ft.add(R.id.container,fragment);
        else
            ft.replace(R.id.container, fragment);
        ft.commit();



    }
}