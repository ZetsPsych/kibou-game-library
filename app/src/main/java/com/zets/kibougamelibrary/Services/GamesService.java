package com.zets.kibougamelibrary.Services;

import com.zets.kibougamelibrary.Entities.Games;
import com.zets.kibougamelibrary.Entities.GamesDao;
import com.zets.kibougamelibrary.Helper.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M4v3r1cX on 08-11-2016.
 */

public class GamesService {

    private GamesDao dao;
    private ArrayList<Games> games;

    public GamesService(){
        dao = DBHelper.getInstance().getGamesDao();
    }

    public Games loadByKey(Long key){ return dao.load(key); }

    public List<Games> loadAll(){ return dao.loadAll(); }

    public void insert(Games entity){
        dao.insert(entity);
    }

    public void update(Games entity){
        dao.update(entity);
    }

    public void delete(Games entity){
        dao.delete(entity);
    }
}
