package com.spectra.fieldforce.model;

public class SendChangeBinRequest {
    private String Authkey;
    private String Action;
    private String SrNumber;
    private String BinId;
    private String NoteTitle;
    private String NoteDes;

    public String getNoteTitle() {
        return NoteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }

    public String getNoteDes() {
        return NoteDes;
    }

    public void setNoteDes(String noteDes) {
        NoteDes = noteDes;
    }

    public String getBinId() {
        return BinId;
    }

    public void setBinId(String binId) {
        BinId = binId;
    }

    public String getAuthkey() {
        return Authkey;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getSrNumber() {
        return SrNumber;
    }

    public void setSrNumber(String srNumber) {
        SrNumber = srNumber;
    }
}
