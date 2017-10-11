package com.zets.kibougamelibrary.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.zets.kibougamelibrary.Entities.Settings;
import com.zets.kibougamelibrary.R;
import com.zets.kibougamelibrary.Services.SettingsService;
import com.zets.kibougamelibrary.Utils.Const;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private int SELECT_PHOTO = 9;

    private SettingsService settingsService;
    Spinner iconNumber;

    Settings setIcons;
    Settings setWall;
    Settings setTitle;
    int selectedPosition;
    String wallpaperRoute = Const.SETTINGS.SETTINGS_WALLPAPER_DEFAULT;
    String customTitle = "";

    Dialog dialogQuestion;
    int dialogAction;

    Button btnChooseWallpaper;
    Button btnWallpaperDefault;
    EditText txtCustomTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSaveSettings:
                questionSettings();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        questionExit();
    }

    private void init() {
        setServices();
        loadData();
        setControls();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgButtonOk:
                if (dialogAction == Const.DIALOG.DIALOG_SAVESETTINGS) {
                    dialogQuestion.dismiss();
                    save();
                } else if (dialogAction == Const.DIALOG.DIALOG_EXITWITHOUTSAVINGSETTINGS) {
                    dialogQuestion.dismiss();
                    cancel();
                } else if (dialogAction == Const.DIALOG.DIALOG_CREDITS) {
                    dialogQuestion.dismiss();
                }
                break;
            case R.id.msgButtonCancel:
                if (dialogAction == Const.DIALOG.DIALOG_SAVESETTINGS) {
                    dialogQuestion.dismiss();
                } else {
                    if (dialogAction == Const.DIALOG.DIALOG_EXITWITHOUTSAVINGSETTINGS) {
                        dialogQuestion.dismiss();
                    }
                }
                break;
            case R.id.stWallpaperButton:
                gotoPickImage();
                break;
            case R.id.stWallpaperButtonDefault:
                setDefaultImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            wallpaperRoute = imagePath;
            createDialog("Success", "Wallpaper set", Const.DIALOG.DIALOG_OK, Const.DIALOG.DIALOG_CREDITS);
        }
    }

    private void setControls() {
        iconNumber = (Spinner) findViewById(R.id.stIconNumber);

        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add(String.valueOf(Const.ICONS_SIZE.ICONS_BIG));
        spinnerArray.add(String.valueOf(Const.ICONS_SIZE.ICONS_MEDIUM));
        spinnerArray.add(String.valueOf(Const.ICONS_SIZE.ICONS_LOW));
        spinnerArray.add(String.valueOf(Const.ICONS_SIZE.ICONS_LOWER));
        spinnerArray.add(String.valueOf(Const.ICONS_SIZE.ICONS_LOWEST));
        spinnerArray.add(String.valueOf(Const.ICONS_SIZE.ICONS_LOWESTER));
        spinnerArray.add(String.valueOf(Const.ICONS_SIZE.ICONS_LOWESTEREST));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, spinnerArray);

        iconNumber.setAdapter(adapter);
        iconNumber.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        iconNumber.setSelection(selectedPosition, true);

        btnChooseWallpaper = (Button) findViewById(R.id.stWallpaperButton);
        btnChooseWallpaper.setOnClickListener(this);
        btnWallpaperDefault = (Button) findViewById(R.id.stWallpaperButtonDefault);
        btnWallpaperDefault.setOnClickListener(this);
        txtCustomTitle = (EditText) findViewById(R.id.stCustomTitleValue);
    }

    private void setServices() {
        settingsService = new SettingsService();
    }

    private void loadData() {
        setIcons = settingsService.loadByName(Const.SETTINGS.SETTINGS_ICONS_SIZE);
        setWall = settingsService.loadByName(Const.SETTINGS.SETTINGS_WALLPAPER);
        setTitle = settingsService.loadByName(Const.SETTINGS.SETTINGS_CUSTOM_TITLE);
        if (setTitle == null){
            setTitle = new Settings();
            setTitle.setName(Const.SETTINGS.SETTINGS_CUSTOM_TITLE);
            setTitle.setValue("");
            setTitle.setId(Const.COMMON_EVENTS.getnewId());
            settingsService.insert(setTitle);
        }

        int selected = Integer.parseInt(setIcons.getValue());

        selectedPosition = selected - 2;
        wallpaperRoute = setWall.getValue();
    }

    private void questionSettings() {
        String title = "U Sure?";
        String text = "Save these settings?";
        createDialog(title, text, Const.DIALOG.DIALOG_OK_CANCEL, Const.DIALOG.DIALOG_SAVESETTINGS);
    }

    private void questionExit() {
        String title = "U Sure?";
        String text = "Exit without saving your settings?";
        createDialog(title, text, Const.DIALOG.DIALOG_OK_CANCEL, Const.DIALOG.DIALOG_EXITWITHOUTSAVINGSETTINGS);
    }

    private void save() {
        customTitle = txtCustomTitle.getText().toString();
        setIcons.setValue(iconNumber.getSelectedItem().toString());
        setWall.setValue(wallpaperRoute);
        setTitle.setValue(customTitle);

        settingsService.update(setIcons);
        settingsService.update(setWall);
        settingsService.update(setTitle);

        setResult(Activity.RESULT_OK);
        finish();
    }

    private void cancel() {
        setResult(Activity.RESULT_CANCELED);
        finish();
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
        } else {
            btnCancel.setOnClickListener(this);
        }
        btnOkay.setOnClickListener(this);

        msgTitle.setText(title);
        msgBody.setText(text);
        dialogAction = action;

        dialogQuestion.show();
    }

    private void gotoPickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private void setDefaultImage(){
        wallpaperRoute = Const.SETTINGS.SETTINGS_WALLPAPER_DEFAULT;
        createDialog("Success", "Default wallpaper set", Const.DIALOG.DIALOG_OK, Const.DIALOG.DIALOG_CREDITS);
    }
}
