package com.spectra.fieldforce.Model;

import java.util.ArrayList;

public class SrDetailsListResponse {
    private ArrayList<Note> Notes ;
    public ArrayList<Note> getNotes() {
        return Notes;
    }

    public void setNotes(ArrayList<Note> Notes) {
        this.Notes = Notes;
    }


    public class Note {
        private String title;
        private String description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
