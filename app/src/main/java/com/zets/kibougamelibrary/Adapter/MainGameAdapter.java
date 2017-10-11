package com.zets.kibougamelibrary.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zets.kibougamelibrary.Activities.MainActivity;
import com.zets.kibougamelibrary.Entities.Games;
import com.zets.kibougamelibrary.R;

import java.util.List;

/**
 * Created by M4v3r1cX on 10-11-2016.
 */

public class MainGameAdapter extends RecyclerView.Adapter<MainGameAdapter.ViewHolder> {

    private Context context;
    private List<Games> gamesList;
    private MainActivity activity;

    public MainGameAdapter(Context context, List<Games> gamesList, MainActivity activity){
        this.context = context;
        this.gamesList = gamesList;
        this.activity = activity;
    }

    @Override
    public MainGameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.activity_main_game, parent, false);
        return new MainGameAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainGameAdapter.ViewHolder holder, int position) {
        final int posicion = position;

        String gameName = gamesList.get(posicion).getAppLabel();
        Drawable gameIcon = gamesList.get(posicion).getAppIcon();

        TextView _gameName = holder.gameName;
        ImageView _gameIcon = holder.gameIcon;
        View _containerView = holder.containerView;

        _gameIcon.setImageDrawable(gameIcon);
        _gameName.setText(gameName);
        _containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGameClick(posicion);
            }
        });
        _containerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onGameLongClick(posicion);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(gamesList.isEmpty()){
            return 0;
        }else{
            return gamesList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView gameName;
        private ImageView gameIcon;
        private View containerView;

        public ViewHolder(View itemView){
            super(itemView);

            gameName = (TextView)itemView.findViewById(R.id.gameLabel);
            gameIcon = (ImageView)itemView.findViewById(R.id.gameImage);
            containerView = itemView.findViewById(R.id.rlGame);
        }
    }

    private void onGameClick(int position){
        Games game = gamesList.get(position);
        activity.launchGame(game);
    }

    private void onGameLongClick(int position){
        Games game = gamesList.get(position);
        activity.optionsGame(game);
    }
}
