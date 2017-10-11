package com.zets.kibougamelibrary.Entities;

import android.graphics.drawable.Drawable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by M4v3r1cX on 07-11-2016.
 */

@Entity(
        active = true,
        nameInDb = "GAMES",
        generateConstructors = true,
        generateGettersSetters = true

)
public class Games {
    @Id(autoincrement = true)
    private Long id;

    private String appName;

    private String appLabel;

    private String appImage;

    @Transient
    private Drawable appIcon;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 691868246)
    private transient GamesDao myDao;

    @Generated(hash = 1643013457)
    public Games(Long id, String appName, String appLabel, String appImage) {
        this.id = id;
        this.appName = appName;
        this.appLabel = appLabel;
        this.appImage = appImage;
    }

    @Generated(hash = 968966035)
    public Games() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppLabel() {
        return this.appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public String getAppImage() {
        return this.appImage;
    }

    public void setAppImage(String appImage) {
        this.appImage = appImage;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 886705344)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGamesDao() : null;
    }
}
