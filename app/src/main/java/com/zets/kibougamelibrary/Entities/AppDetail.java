package com.zets.kibougamelibrary.Entities;

import android.graphics.drawable.Drawable;

/**
 * Created by M4v3r1cX on 09-11-2016.
 */

public class AppDetail {
    CharSequence label;
    CharSequence name;
    Drawable icon;

    public AppDetail() {
    }

    public AppDetail(CharSequence label, CharSequence name, Drawable icon) {
        this.label = label;
        this.name = name;
        this.icon = icon;
    }

    public CharSequence getLabel() {
        return label;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
