package com.zets.kibougamelibrary.Services;

import com.zets.kibougamelibrary.Entities.Settings;
import com.zets.kibougamelibrary.Entities.SettingsDao;
import com.zets.kibougamelibrary.Helper.DBHelper;
import com.zets.kibougamelibrary.Utils.Const;

import java.util.List;

/**
 * Created by Machina on 13-11-2016.
 */
public class SettingsService {
    private SettingsDao dao;
    //private ArrayList<Games> games;

    public SettingsService(){
        dao = DBHelper.getInstance().getSettingsDao();
    }

    public List<Settings> loadAll(){ return dao.loadAll(); }

    public Settings loadByName(String name){
        return dao.queryBuilder().where(SettingsDao.Properties.Name.eq(name)).unique();
    }

    public void insert(Settings entity){
        dao.insert(entity);
    }

    public void update(Settings entity){
        dao.update(entity);
    }

    public void delete(Settings entity){
        dao.delete(entity);
    }

    public void initSettings(){
        Settings settingsIcons = new Settings();
        settingsIcons.setId(Const.COMMON_EVENTS.getnewId());
        settingsIcons.setName(Const.SETTINGS.SETTINGS_ICONS_SIZE);
        settingsIcons.setValue(String.valueOf(Const.ICONS_SIZE.ICONS_BIG));
        insert(settingsIcons);
        Settings settingsWall = new Settings();
        settingsWall.setId(Const.COMMON_EVENTS.getnewId());
        settingsWall.setName(Const.SETTINGS.SETTINGS_WALLPAPER);
        settingsWall.setValue(Const.SETTINGS.SETTINGS_WALLPAPER_DEFAULT);
        insert(settingsWall);
    }
}
