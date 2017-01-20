package com.spps.mandal.Adapter;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.spps.mandal.AddYaatra;
import com.spps.mandal.EditYaatra;
import com.spps.mandal.Feedback;
import com.spps.mandal.ImageForAds;
import com.spps.mandal.MainActivity;
import com.spps.mandal.Model.DrawerItems;
import com.spps.mandal.R;
import com.spps.mandal.SessionManager.SessionManager;
import com.spps.mandal.ShowYaatra;
import com.spps.mandal.Singleton.DrawerListInstance;

import java.util.ArrayList;

public class DrawerAdapterForGuruSwami extends RecyclerView.Adapter<DrawerAdapterForGuruSwami.ViewHolder> {
    private static ArrayList<DrawerItems> itemsArrayList;
    public View v;
    public ViewHolder viewHolder;
    public DrawerLayout drawer;
    int positionOfItem = 0;

    private static ArrayList<DrawerItems> itemselectedArrayList;
    public static DrawerListInstance drawerListInstance = new DrawerListInstance();

    int imageInstance;

    public DrawerAdapterForGuruSwami(ArrayList<DrawerItems> itemArrayListForNgo, ArrayList<DrawerItems> itemSelectedArrayListForNgo, DrawerLayout drawer) {
        this.drawer = drawer;
        this.itemsArrayList = itemArrayListForNgo;
        this.itemselectedArrayList = itemSelectedArrayListForNgo;
    }

    @Override
    public DrawerAdapterForGuruSwami.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.draweritems, viewGroup, false);
        } else if (i == 1) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.draweritemtext, viewGroup, false);
        }
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerAdapterForGuruSwami.ViewHolder viewHolder, int i) {
        DrawerItems itemsList = itemsArrayList.get(i);
        DrawerItems itemselectedList = itemselectedArrayList.get(i);
        viewHolder.bindDrawerItemArrayList(i, itemsList, itemselectedList);
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (position <= 5) {
            viewType = 0;
        } else if (position > 5) {
            viewType = 1;
        }
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView vtextView;
        public ImageView vimageView;
        public SessionManager sessionManager;
        public View drawerDivider;
        public TextView vtext;
        String counter;

        private DrawerItems itemsList;
        private DrawerItems itemsSelectedList;

        public ArrayList<DrawerItems> drawerItemsArrayList = new ArrayList<DrawerItems>();

        public ViewHolder(View itemView) {
            super(itemView);
            vtextView = (TextView) itemView.findViewById(R.id.drawerItemText);
            vimageView = (ImageView) itemView.findViewById(R.id.drawerItemIcon);
            drawerDivider = (View) itemView.findViewById(R.id.drawerDivider);
            vtext = (TextView) itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener(this);
        }

        public void bindDrawerItemArrayList(int i, DrawerItems draweritemsList, DrawerItems draweritemselectedList) {

            this.itemsList = draweritemsList;
            this.itemsSelectedList = draweritemselectedList;

            if (drawerListInstance.getDrawerItemListImagePositionInstances() != null) {
                positionOfItem = drawerListInstance.getDrawerItemListImagePositionInstances();
            }
            if (i <= 5) {
                vimageView.setImageResource(itemsList.getIcon());
                vtextView.setText(itemsList.getTittle());
                //vimageView.setEnabled(true);
                if (i == 5) {
                    drawerDivider.setVisibility(View.VISIBLE);
                }
                if (positionOfItem == 0 && itemsList.getIcon() == R.drawable.home) {
                    vtextView.setText(itemsSelectedList.getTittle());
                    vimageView.setImageResource(itemsSelectedList.getIcon());
                }
                else if (positionOfItem == 1 && itemsList.getIcon() == R.drawable.add) {
                    vtextView.setText(itemsSelectedList.getTittle());
                    vimageView.setImageResource(itemsSelectedList.getIcon());
                }
                else if (positionOfItem == 2 && itemsList.getIcon() == R.drawable.edit) {
                    vtextView.setText(itemsSelectedList.getTittle());
                    vimageView.setImageResource(itemsSelectedList.getIcon());
                }
                else if (positionOfItem == 3 && itemsList.getIcon() == R.drawable.news) {
                    vtextView.setText(itemsSelectedList.getTittle());
                    vimageView.setImageResource(itemsSelectedList.getIcon());
                }
                else if (positionOfItem == 4 && itemsList.getIcon() == R.drawable.logout) {
                    vtextView.setText(itemsSelectedList.getTittle());
                    vimageView.setImageResource(itemsSelectedList.getIcon());
                }

            } else if (i > 5) {
                vtext.setText(itemsList.getTittle());
            }
        }

        @Override
        public void onClick(View view) {
            positionOfItem = this.getAdapterPosition();
            if (this.getAdapterPosition() == 0) {
                drawer.closeDrawers();
                Intent gotoformupload = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(gotoformupload);
            }
            else if (this.getAdapterPosition() == 1) {
                counter="2";
                drawer.closeDrawers();
                Intent gotoPasswordForEdit = new Intent(view.getContext(), ImageForAds.class);
                gotoPasswordForEdit.putExtra("counter", counter);
                view.getContext().startActivity(gotoPasswordForEdit);
            }
            else if (this.getAdapterPosition() == 2) {
                counter="3";
                drawer.closeDrawers();
                Intent gotomylisting = new Intent(v.getContext(), ImageForAds.class);
                gotomylisting.putExtra("counter", counter);
                v.getContext().startActivity(gotomylisting);
            }
            else if (this.getAdapterPosition() == 3) {
                counter="4";
                drawer.closeDrawers();
                Intent gotoWishList = new Intent(v.getContext(), ImageForAds.class);
                gotoWishList.putExtra("counter", counter);
                v.getContext().startActivity(gotoWishList);
            }else if (this.getAdapterPosition() == 4) {
                drawer.closeDrawers();
                Intent gotoWishList = new Intent(v.getContext(), Feedback.class);
                v.getContext().startActivity(gotoWishList);
            }
            else if (this.getAdapterPosition() == 5) {
                drawer.closeDrawers();
                sessionManager = new SessionManager(v.getContext());
                sessionManager.logoutUser();
            }
            drawerListInstance.setDrawerItemListImagePositionInstances(positionOfItem);
            notifyDataSetChanged();
        }
    }
}