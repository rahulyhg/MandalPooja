package com.spps.mandal.Adapter;

import com.spps.mandal.Model.YaatraItems;
import com.spps.mandal.R;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class YaatraDetailsAdapter extends RecyclerView.Adapter<YaatraDetailsAdapter.ViewHolder>{

    List<YaatraItems> notificationItems;
    View v;
    RecyclerView.ViewHolder viewHolder;

    public YaatraDetailsAdapter(List<YaatraItems> yaatraItems) {
        this.notificationItems = yaatraItems;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_yaatra_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        YaatraItems notificationItem = notificationItems.get(position);
        viewHolder.bindNotificationDetailsList(notificationItem );
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mandalName;
        public TextView guruswamiName;
        public TextView dateOfMandalPooja;
        public TextView dateOfYaatra;
        public TextView dateOfDarshan;
        public View cardView;

        YaatraItems notificationItems = new YaatraItems();

        public ViewHolder(View itemView) {
            super(itemView);

            mandalName = (TextView) itemView.findViewById(R.id.mandalName);
            guruswamiName = (TextView) itemView.findViewById(R.id.guruswamiName);
            dateOfMandalPooja = (TextView) itemView.findViewById(R.id.dateOfMandalPooja);
            dateOfYaatra = (TextView) itemView.findViewById(R.id.dateOfYaatra);
            dateOfDarshan = (TextView) itemView.findViewById(R.id.dateOfDarshan);
            cardView = itemView;
        }

        public void bindNotificationDetailsList(YaatraItems notificationItems) {
            this.notificationItems = notificationItems;

            mandalName.setText(notificationItems.getmandalName());
            guruswamiName.setText(notificationItems.getguruswamiNames());
            dateOfMandalPooja.setText(notificationItems.getdateOfMandalPooja());
            dateOfYaatra.setText(notificationItems.getdateOfMandalYaatra());
            dateOfDarshan.setText(notificationItems.getdateOfMandalDarshan());

        }
    }
}
