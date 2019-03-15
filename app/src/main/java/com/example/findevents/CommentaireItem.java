package com.example.findevents;

public class CommentaireItem {
    private int id_user;
    private int id_event;
    private int id_commentaire;
    private String date;
    private String message;

    public CommentaireItem(int id_user, int id_event, int id_commentaire, String date, String message) {
        this.id_user = id_user;
        this.id_event = id_event;
        this.id_commentaire = id_commentaire;
        this.date = date;
        this.message = message;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public void setId_commentaire(int id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_event() {
        return id_event;
    }

    public int getId_commentaire() {
        return id_commentaire;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
