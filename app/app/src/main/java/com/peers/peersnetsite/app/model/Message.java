package com.peers.peersnetsite.app.model;

import java.util.Date;

/**
 * Created by ipinyol on 7/12/14.
 */
public class Message {
    private String UUID;
    private String message;
    private String url;
    private Date date;

    private String referTo_Message_UUID;
    private String from_Peer_UUID;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReferTo_Message_UUID() {
        return referTo_Message_UUID;
    }

    public void setReferTo_Message_UUID(String referTo_Message_UUID) {
        this.referTo_Message_UUID = referTo_Message_UUID;
    }

    public String getFrom_Peer_UUID() {
        return from_Peer_UUID;
    }

    public void setFrom_Peer_UUID(String from_Peer_UUID) {
        this.from_Peer_UUID = from_Peer_UUID;
    }
}
