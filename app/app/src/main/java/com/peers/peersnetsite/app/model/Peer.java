package com.peers.peersnetsite.app.model;

/**
 * Created by ipinyol on 7/12/14.
 */
public class Peer {
    private String UUID;
    private boolean isSelf;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }
}
