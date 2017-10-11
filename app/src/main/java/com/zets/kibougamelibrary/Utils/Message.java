package com.zets.kibougamelibrary.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by M4v3r1cX on 10-11-2016.
 */

public class Message {

    public static void showMessageOk(Context context, String text, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(text)
                .setCancelable(false)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean showMessageOkCancel(Context context, String text, String title){
        final boolean[] ret = new boolean[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(text)
                .setCancelable(false)
                .setTitle(title)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ret[0] = false;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ret[0] = true;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        return ret[0];
    }
}
