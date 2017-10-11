package com.zets.kibougamelibrary.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zets.kibougamelibrary.Adapter.AddGameAdapter;
import com.zets.kibougamelibrary.Entities.AppDetail;
import com.zets.kibougamelibrary.Entities.Games;
import com.zets.kibougamelibrary.R;
import com.zets.kibougamelibrary.Services.GamesService;
import com.zets.kibougamelibrary.Utils.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddGameActivity extends AppCompatActivity {

    private List<AppDetail> appList;
    private PackageManager manager;

    RecyclerView recyclerView;
    AddGameAdapter adapter;
    GamesService gamesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        init();
    }

    private void init(){
        setServices();
        setControls();
        setToolbar();
        loadData();
    }

    private void setServices(){
        gamesService = new GamesService();
    }

    private void setControls(){
        recyclerView = (RecyclerView)findViewById(R.id.addGameRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setToolbar(){
        getSupportActionBar().setTitle("Please choose an App.");
    }

    private void loadData(){
        loadApps();
        setRecyclerView();
    }

    private void loadApps() {
        manager = getPackageManager();
        appList = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> avaliableActivities = manager.queryIntentActivities(i, 0);
        //List<ApplicationInfo> apps = manager.getInstalledApplications(0);
        //Collections.sort(apps, new ApplicationInfo.DisplayNameComparator(manager));

        Collections.sort(avaliableActivities, new ResolveInfo.DisplayNameComparator(manager));

        for (ResolveInfo ri : avaliableActivities){
            AppDetail app = new AppDetail();
            app.setLabel(ri.loadLabel(manager));
            app.setName(ri.activityInfo.packageName);
            app.setIcon(ri.activityInfo.loadIcon(manager));
            appList.add(app);
        }
        /*for (ApplicationInfo a : apps){
            AppDetail app = new AppDetail();
            app.setLabel(a.loadLabel(manager));
            app.setName(a.packageName);
            app.setIcon(a.loadIcon(manager));
            appList.add(app);
        }*/
    }

    private void setRecyclerView(){
        adapter = new AddGameAdapter(this, appList, this);
        recyclerView.setAdapter(adapter);
    }

    public void onSelectApp(AppDetail app){
        Games game = new Games();
        game.setAppLabel(app.getLabel().toString());
        game.setAppName(app.getName().toString());
        gamesService.insert(game);

        Message.showMessageOk(this, "Game saved!", "Ready!");
        setResult(Activity.RESULT_OK);
        this.finish();
    }
}
