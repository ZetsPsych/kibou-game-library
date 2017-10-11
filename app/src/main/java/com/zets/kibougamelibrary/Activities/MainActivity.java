package com.zets.kibougamelibrary.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zets.kibougamelibrary.Adapter.MainGameAdapter;
import com.zets.kibougamelibrary.Entities.Games;
import com.zets.kibougamelibrary.Entities.Settings;
import com.zets.kibougamelibrary.R;
import com.zets.kibougamelibrary.Services.GamesService;
import com.zets.kibougamelibrary.Services.SettingsService;
import com.zets.kibougamelibrary.Utils.Const;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    ImageView wallpaper;
    Dialog dialogMenu;
    Button addGame;
    Button about;
    Button settings;

    Dialog dialogLoading;
    Dialog dialogQuestion;

    Games selectedGame;
    private int dialogAction;

    GamesService gamesService;
    SettingsService settingsService;
    private List<Games> gamesList;
    private List<Settings> allSettings;
    private MainGameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuOpenDialog:
                openDialogMenu();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE.REQUEST_ADD_GAME) {
            if (resultCode == Activity.RESULT_OK) {
                loadData();
                dialogLoading.dismiss();
            } else {
                dialogLoading.dismiss();
            }
        } else if (requestCode == Const.REQUEST_CODE.REQUEST_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                loadData();
            }
        }
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    private void init() {
        setServices();
        setControls();
        loadData();
        setToolbar();

    }

    private void setServices() {
        gamesService = new GamesService();
        settingsService = new SettingsService();
    }

    private void setControls() {
        recyclerView = (RecyclerView) findViewById(R.id.rvMain);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        wallpaper = (ImageView) findViewById(R.id.imgBackgroundMain);
        createDialogs();
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.controller);
    }

    private void loadData() {
        gamesList = new ArrayList<>();
        gamesList = gamesService.loadAll();

        allSettings = new ArrayList<>();
        allSettings = settingsService.loadAll();

        if (gamesList.size() == 0 && allSettings.size() == 0) {
            settingsService.initSettings();
            allSettings = settingsService.loadAll();
            welcomeMessage();
        } else {
            loadIcons();
            loadRecyclerView();
        }

        loadWallpaper();
        loadCustomTitle();
    }

    private void loadIcons() {
        for (Games game : gamesList) {
            try {
                Drawable icon = getPackageManager().getApplicationIcon(game.getAppName());
                game.setAppIcon(icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadWallpaper() {
        Settings settings = new Settings();
        settings = settingsService.loadByName(Const.SETTINGS.SETTINGS_WALLPAPER);
        if (!settings.getValue().equals(Const.SETTINGS.SETTINGS_WALLPAPER_DEFAULT)) {
            Drawable back = Drawable.createFromPath(settings.getValue());
            wallpaper.setImageDrawable(back);
            wallpaper.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            wallpaper.setImageDrawable(null);
        }
    }

    private void loadCustomTitle() {
        Settings settings = new Settings();
        settings = settingsService.loadByName(Const.SETTINGS.SETTINGS_CUSTOM_TITLE);
        if (settings != null && !settings.getValue().equals("")){
            getSupportActionBar().setTitle("  " + settings.getValue());
        } else {
            getSupportActionBar().setTitle("  " + getString(R.string.app_name));
        }
    }

    private void loadRecyclerView() {
        Settings settings = new Settings();
        settings = settingsService.loadByName(Const.SETTINGS.SETTINGS_ICONS_SIZE);
        recyclerView.setLayoutManager(new GridLayoutManager(this,
                Integer.parseInt(settings.getValue())));
        adapter = new MainGameAdapter(this, gamesList, this);
        recyclerView.setAdapter(adapter);
    }

    public void launchGame(Games games) {
        Intent i = getPackageManager().getLaunchIntentForPackage(games.getAppName());
        startActivity(i);
    }

    public void optionsGame(final Games game) {
        selectedGame = game;
        String title = "U sure?";
        String text = "Delete game shortcut?";
        createDialog(title, text, Const.DIALOG.DIALOG_OK_CANCEL, Const.DIALOG.DIALOG_DELETE);
    }

    private void createDialogs() {
        dialogMenu = new Dialog(this);
        dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMenu.setContentView(R.layout.dialog_main_menu);

        addGame = (Button) dialogMenu.findViewById(R.id.btnDialogAddGame);
        about = (Button) dialogMenu.findViewById(R.id.btnDialogAbout);
        settings = (Button) dialogMenu.findViewById(R.id.btnDialogSettings);

        addGame.setOnClickListener(this);
        about.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    private void openDialogMenu() {
        dialogMenu.show();
    }

    private void addGame() {
        dialogLoading();
        Intent intent = new Intent(this, AddGameActivity.class);
        startActivityForResult(intent, Const.REQUEST_CODE.REQUEST_ADD_GAME);
    }

    private void welcomeMessage() {
        String text = "Hello and welcome to GLib Games Library! Your number one stop for all your " +
                "shortcut needs.\nTo get started, choose the Add New Game option on the menu." +
                "\nHave Fun!";
        String title = "Welcome!";
        createDialog(title, text, Const.DIALOG.DIALOG_OK, Const.DIALOG.DIALOG_CREDITS);
    }

    private void about() {
        String text = "Glib Game Library " + Const.VERSION.VERSION + "\nAnother weird experiment brought to you by " +
                "BSOD Software.";
        String title = "About...";
        createDialog(title, text, Const.DIALOG.DIALOG_OK, Const.DIALOG.DIALOG_CREDITS);
    }

    private void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, Const.REQUEST_CODE.REQUEST_SETTINGS);
    }

    private void dialogLoading() {
        dialogLoading = new Dialog(this);
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDialogAddGame:
                addGame();
                dialogMenu.dismiss();
                break;
            case R.id.btnDialogSettings:
                settings();
                dialogMenu.dismiss();
                break;
            case R.id.btnDialogAbout:
                about();
                dialogMenu.dismiss();
                break;
            case R.id.msgButtonOk:
                if (dialogAction == Const.DIALOG.DIALOG_CREDITS) {
                    dialogQuestion.dismiss();
                }
                if (dialogAction == Const.DIALOG.DIALOG_DELETE) {
                    gamesService.delete(selectedGame);
                    loadData();
                    dialogQuestion.dismiss();
                }
                break;
            case R.id.msgButtonCancel:
                dialogQuestion.dismiss();
                break;
        }
    }

    private void createDialog(String title, String text, int dialogType, int action) {
        dialogQuestion = new Dialog(this);
        dialogQuestion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogQuestion.setContentView(R.layout.dialog_message);

        Button btnOkay = (Button) dialogQuestion.findViewById(R.id.msgButtonOk);
        Button btnCancel = (Button) dialogQuestion.findViewById(R.id.msgButtonCancel);
        TextView msgTitle = (TextView) dialogQuestion.findViewById(R.id.msgTitle);
        TextView msgBody = (TextView) dialogQuestion.findViewById(R.id.msgBody);

        if (dialogType == Const.DIALOG.DIALOG_OK) {
            btnCancel.setVisibility(View.GONE);
        }

        btnOkay.setOnClickListener(this);

        msgTitle.setText(title);
        msgBody.setText(text);
        dialogAction = action;

        dialogQuestion.show();
    }

}
