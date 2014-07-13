package com.peers.peersnetsite.app.db;

/**
 * Created by ipinyol on 7/13/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.peers.peersnetsite.app.model.Message;
import com.peers.peersnetsite.app.model.Peer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { DBHelper.COL_MESSAGE_UUID,
                                    DBHelper.COL_MESSAGE_MESSAGE,
                                    DBHelper.COL_MESSAGE_URL,
                                    DBHelper.COL_MESSAGE_DATE,
                                    DBHelper.COL_MESSAGE_REFERTO,
                                    DBHelper.COL_MESSAGE_FROMPEER};

    public MessageDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Creates a new {@link Message}
     * @param UUID
     * @param message
     * @param url
     * @param date
     * @param referTo
     * @param from
     * @return The newly created {@link Message}
     */
    public Message newMessage(String UUID, String message, String url, String date, String referTo,
                              String from) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_MESSAGE_UUID, UUID);
        values.put(DBHelper.COL_MESSAGE_MESSAGE, message);
        values.put(DBHelper.COL_MESSAGE_URL, url);
        values.put(DBHelper.COL_MESSAGE_DATE, date);
        values.put(DBHelper.COL_MESSAGE_REFERTO, referTo);
        values.put(DBHelper.COL_MESSAGE_FROMPEER, from);
        database.insert(DBHelper.TABLE_MESSAGE, null, values);
        Cursor cursor = database.query(DBHelper.TABLE_MESSAGE,
                allColumns, DBHelper.COL_MESSAGE_UUID + " = '" + UUID + "'", null, null, null, null);
        cursor.moveToFirst();
        Message newMessage = cursorToMessage(cursor);
        cursor.close();
        return newMessage;
    }

    /**
     * Returns the {@link Message} given its UUID
     * @param UUID
     * @return
     */
    public Message getMessage(String UUID) {
        String filter = DBHelper.COL_MESSAGE_UUID + " = '" + UUID + "'";
        Cursor cursor = database.query(DBHelper.TABLE_MESSAGE, allColumns, filter, null, null,
                null, null);
        cursor.moveToFirst();
        Message message = cursorToMessage(cursor);
        cursor.close();
        return message;
    }

    /**
     * Return the list of all messages ordered by date DESC
     * @return List of {@link Message}
     */
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<Message>();
        String orderBy = DBHelper.COL_MESSAGE_DATE + " DESC";
        Cursor cursor = database.query(DBHelper.TABLE_MESSAGE, allColumns, null, null,
                null, null, orderBy);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message= cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    private Message cursorToMessage(Cursor cursor) {

        Message message = new Message();
        message.setUUID(cursor.getString(0));
        message.setMessage(cursor.getString(1));
        message.setUrl(cursor.getString(2));
        Date date = null;
        try {
            date = Message.dateFormat.parse(cursor.getString(3));
        } catch (ParseException e) {}
        message.setDate(date);
        message.setReferTo_Message_UUID(cursor.getString(4));
        message.setFrom_Peer_UUID(cursor.getColumnName(5));
        return message;
    }
}
