package com.zets.kibougamelibrary.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zets.kibougamelibrary.Entities.DaoMaster;
import com.zets.kibougamelibrary.Entities.DaoSession;
import com.zets.kibougamelibrary.Entities.GamesDao;
import com.zets.kibougamelibrary.Entities.SettingsDao;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by M4v3r1cX on 08-11-2016.
 */

public class DBHelper {
    private static DBHelper INSTANCE = null;
    private static boolean DATABASEINIT = false;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public static DBHelper getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DBHelper();
        }
        return INSTANCE;
    }

    public void init(Context context){
        String directory = context.getExternalFilesDir(null).getParent();
        String databaseName = directory + "/glib.db";

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, databaseName);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        DATABASEINIT = true;
    }

    public SQLiteDatabase getDb(){ return db; }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public void beginTransaction(AbstractDao dao) {
        dao.getDatabase().beginTransaction();
    }

    public GamesDao getGamesDao(){ return daoSession.getGamesDao(); }

    public SettingsDao getSettingsDao(){ return daoSession.getSettingsDao(); }
}
