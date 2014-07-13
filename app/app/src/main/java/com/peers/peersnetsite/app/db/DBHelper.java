package com.peers.peersnetsite.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ipinyol on 7/12/14.
 */

public class DBHelper extends SQLiteOpenHelper{

    public  static final String TABLE_PEER = "peer";
    public  static final String COL_PEER_UUID = "uuid";
    public  static final String COL_PEER_ISSELF = "isSelf";
    public  static final String COL_PEER_NAME = "name";

    public  static final String TABLE_MESSAGE = "message";
    public  static final String COL_MESSAGE_UUID = "uuid";
    public  static final String COL_MESSAGE_MESSAGE = "message";
    public  static final String COL_MESSAGE_URL = "url";
    public  static final String COL_MESSAGE_REFERTO = "referTo_Message_UUID";
    public  static final String COL_MESSAGE_FROMPEER = "from_Peer_UUID";

    private static final String tablePeer =
                                "CREATE TABLE " + TABLE_PEER + "(" +
                                COL_PEER_UUID + " TEXT PRIMARY KEY," +
                                COL_PEER_ISSELF + " INTEGER," +
                                COL_PEER_NAME + " TEXT);";

    private static final String tableMessage =
                                "CREATE TABLE " + TABLE_MESSAGE + "(" +
                                COL_MESSAGE_UUID + " TEXT PRIMARY KEY," +
                                COL_MESSAGE_MESSAGE + " TEXT," +
                                COL_MESSAGE_URL +" TEXT," +
                                COL_MESSAGE_REFERTO + " TEXT," +
                                COL_MESSAGE_FROMPEER + " TEXT," +
                                "FOREIGN KEY(" + COL_MESSAGE_REFERTO+ ") " +
                                    "REFERENCES " + TABLE_MESSAGE+ "(" + COL_MESSAGE_UUID +")," +
                                "FOREIGN KEY(" + COL_MESSAGE_REFERTO + ")" +
                                    " REFERENCES " + TABLE_PEER + "(" + COL_PEER_UUID+ "));";

    private String sqlCreate = tablePeer + tableMessage;
    public DBHelper(Context context, String name,
                                CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migrate both versions
        //db.execSQL("DROP TABLE IF EXISTS Usuarios");
        //db.execSQL(sqlCreate);
    }
}
