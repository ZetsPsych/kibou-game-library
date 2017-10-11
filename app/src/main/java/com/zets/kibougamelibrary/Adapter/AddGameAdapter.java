package com.zets.kibougamelibrary.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zets.kibougamelibrary.Activities.AddGameActivity;
import com.zets.kibougamelibrary.Entities.AppDetail;
import com.zets.kibougamelibrary.R;

import java.util.List;

/**
 * Created by M4v3r1cX on 09-11-2016.
 */

public class AddGameAdapter extends RecyclerView.Adapter<AddGameAdapter.ViewHolder> {

    private Context context;
    private List<AppDetail> appDetails;
    private AddGameActivity activity;

    public AddGameAdapter(Context context, List<AppDetail> appList, AddGameActivity activity){
        this.context = context;
        this.appDetails = appList;
        this.activity = activity;
    }

    @Override
    public AddGameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.activity_add_game_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddGameAdapter.ViewHolder holder, int position) {
        final int posicion = position;

        CharSequence appPackage = appDetails.get(posicion).getName();
        CharSequence appLabel = appDetails.get(posicion).getLabel();
        Drawable appIcon = appDetails.get(posicion).getIcon();

        TextView _appLabel = holder.appLabel;
        TextView _appPackage = holder.appPackage;
        ImageView _appIcon = holder.appIcon;
        View containerView = holder.containerView;

        _appLabel.setText(appLabel);
        _appPackage.setText(appPackage);
        _appIcon.setImageDrawable(appIcon);

        containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAppClick(posicion);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(appDetails.isEmpty()){
            return 0;
        }else{
            return appDetails.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView appLabel;
        private TextView appPackage;
        private ImageView appIcon;
        private View containerView;

        public ViewHolder(View itemView){
            super(itemView);

            appLabel = (TextView)itemView.findViewById(R.id.appLabel);
            appPackage = (TextView)itemView.findViewById(R.id.appPackage);
            appIcon = (ImageView)itemView.findViewById(R.id.appIcon);
            containerView = itemView.findViewById(R.id.card_layout);
        }

    }

    private void onAppClick(int position){
        AppDetail selectedApp = appDetails.get(position);
        activity.onSelectApp(selectedApp);
    }
}
