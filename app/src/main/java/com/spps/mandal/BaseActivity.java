package com.spps.mandal;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.spps.mandal.Adapter.DrawerAdapter;
import com.spps.mandal.Adapter.DrawerAdapterForGuruSwami;
import com.spps.mandal.Model.DrawerItems;
import com.spps.mandal.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    SessionManager sessionManager;
    TextView lblName;
    RecyclerView listItems;
    DrawerLayout drawer;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    DrawerAdapterForGuruSwami drawerAdapterForGuruSwami;
    DrawerAdapter drawerAdapter;
    //String userType ="GuruSwami";
    String userType;

    public ArrayList<DrawerItems> itemArrayList;
    public ArrayList<DrawerItems> itemSelectedArrayListForNgo;

    public ArrayList<DrawerItems> itemArrayListForNgo;
    public ArrayList<DrawerItems> itemSelectedArrayList;

    @Override
    public void setContentView(int layoutResID) {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer, null);
        frameLayout = (FrameLayout) drawer.findViewById(R.id.contentFrame);
        linearLayout = (LinearLayout) drawer.findViewById(R.id.drawerlinearlayout);
        listItems = (RecyclerView) drawer.findViewById(R.id.drawerListItem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItems.setLayoutManager(linearLayoutManager);

        SessionManager sessionManagerNgo = new SessionManager(BaseActivity.this);
        HashMap<String, String> typeOfUser = sessionManagerNgo.getUserDetails();
        userType = typeOfUser.get(SessionManager.KEY_USERTYPE);

//        if (userType == null) {
//            sessionManager = new SessionManager(BaseActivity.this);
//            sessionManager.logoutUser();
//
//        } else {
            //for Parents user GuruSwami
            if (userType.equals("Guruswami")) {
                final String[] tittleForNgo = new String[]{"Home","Add Details ","Edit Details","Yaatra Information", "Feedback","Logout"};

                final int[] iconsForNgo = new int[]{R.drawable.home, R.drawable.add, R.drawable.edit, R.drawable.news,R.drawable.feedback,R.drawable.logout };
                itemArrayListForNgo = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittleForNgo.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittleForNgo[i]);
                    drawerItems.setIcons(iconsForNgo[i]);
                    itemArrayListForNgo.add(drawerItems);
                }

                final int[] selectediconsForNgo = new int[]{R.drawable.home_red, R.drawable.add, R.drawable.edit, R.drawable.news_red,R.drawable.feedback, R.drawable.logout,};
                itemSelectedArrayListForNgo = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittleForNgo.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittleForNgo[i]);
                    drawerItems.setIcons(selectediconsForNgo[i]);
                    itemSelectedArrayListForNgo.add(drawerItems);
                }
                drawerAdapterForGuruSwami = new DrawerAdapterForGuruSwami(itemArrayListForNgo, itemSelectedArrayListForNgo, drawer);
                getLayoutInflater().inflate(layoutResID, frameLayout, true);
                getLayoutInflater().inflate(layoutResID, linearLayout, true);
                drawer.setClickable(true);
                drawerAdapterForGuruSwami.notifyDataSetChanged();
                listItems.setAdapter(drawerAdapterForGuruSwami);
            }
            // for Normal user
            else{
                final String[] tittle =  new String[]{"Home","Yaatra Information","Feedback", "Logout"};
                final int[] icons = new int[]{R.drawable.home,R.drawable.news,R.drawable.feedback, R.drawable.logout };
                itemArrayList = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittle.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittle[i]);
                    drawerItems.setIcons(icons[i]);
                    itemArrayList.add(drawerItems);
                }
                final int[] selectedicons = new int[]{R.drawable.home_red, R.drawable.news_red,R.drawable.feedback,R.drawable.logout,};
                itemSelectedArrayList = new ArrayList<DrawerItems>();
                for (int i = 0; i < tittle.length; i++) {
                    DrawerItems drawerItems = new DrawerItems();
                    drawerItems.setTittle(tittle[i]);
                    drawerItems.setIcons(selectedicons[i]);
                    itemSelectedArrayList.add(drawerItems);
                }
                drawerAdapter = new DrawerAdapter(itemArrayList, itemSelectedArrayList, drawer);
                getLayoutInflater().inflate(layoutResID, frameLayout, true);
                getLayoutInflater().inflate(layoutResID, linearLayout, true);
                drawer.setClickable(true);
                drawerAdapter.notifyDataSetChanged();
                listItems.setAdapter(drawerAdapter);
            //}
        }

        toolbar = (Toolbar) drawer.findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        super.setContentView(drawer);
        sessionManager = new SessionManager(getApplicationContext());
        lblName = (TextView) findViewById(R.id.lblName);

        HashMap<String, String> user = sessionManager.getUserDetails();

        String userId = user.get(SessionManager.KEY_USERID);  // get email
        String name = user.get(SessionManager.KEY_NAME);  // get Name
        lblName.setText(name);   // Show user data on activity
        //changeAppFont();
    }

//    public void changeAppFont() {
//        setDefaultFont(this, "MONOSPACE", "fonts/Montserrat-Regular.ttf");
//
//    }
//
//    public static void setDefaultFont(Context context,
//                                      String staticTypefaceFieldName, String fontAssetName) {
//        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
//                fontAssetName);
//        replaceFont(staticTypefaceFieldName, regular);
//    }
//
//    public static void replaceFont(String staticTypefaceFieldName,
//                                   final Typeface newTypeface) {
//
//        if (Build.VERSION.SDK_INT > 16) {
//            try {
//                final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
//                staticField.setAccessible(true);
//                staticField.set(null, newTypeface);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}