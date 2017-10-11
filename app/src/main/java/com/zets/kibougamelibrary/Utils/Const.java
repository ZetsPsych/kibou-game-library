package com.zets.kibougamelibrary.Utils;

import java.util.Date;

/**
 * Created by Machina on 13-11-2016.
 */
public class Const {
    public static class ICONS_SIZE {
        public static final int ICONS_BIG = 2;
        public static final int ICONS_MEDIUM = 3;
        public static final int ICONS_LOW = 4;
        public static final int ICONS_LOWER = 5;
        public static final int ICONS_LOWEST = 6;
        public static final int ICONS_LOWESTER = 7;
        public static final int ICONS_LOWESTEREST = 8;
    }

    public static class REQUEST_CODE {
        public static final int REQUEST_ADD_GAME = 2501;
        public static final int REQUEST_SETTINGS = 8888;
    }

    public static class VERSION {
        public static final String VERSION = "v0.9";
    }

    public static class DIALOG {
        public static final int DIALOG_OK = 101;
        public static final int DIALOG_OK_CANCEL = 102;
        public static final int DIALOG_CREDITS = 103;
        public static final int DIALOG_DELETE = 104;
        public static final int DIALOG_SAVESETTINGS = 105;
        public static final int DIALOG_EXITWITHOUTSAVINGSETTINGS = 106;
    }

    public static class SETTINGS {
        public static final String SETTINGS_ICONS_SIZE = "Settings_Icons";
        public static final String SETTINGS_WALLPAPER = "Settings_Wallpaper";
        public static final String SETTINGS_WALLPAPER_DEFAULT = "Settings_Wallpaper_Default";
        public static final String SETTINGS_CUSTOM_TITLE = "Settings_Custom_Title";
    }

    public static class COMMON_EVENTS {
        public static Long getnewId(){
            return new Date().getTime();
        }
    }
}
