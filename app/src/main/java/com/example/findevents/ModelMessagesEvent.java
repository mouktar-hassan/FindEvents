package com.example.findevents;

public class ModelMessagesEvent {
    private int idMessage;
    private int idUser;
    private int idEvent;
    private String titreEvent;
    private String pseudoEcrivain;
    private String messageEvent;
    private String date;

    public ModelMessagesEvent(int idMessage, int idUser, int idEvent, String titreEvent, String pseudoEcrivain, String messageEvent, String date) {
        this.idMessage = idMessage;
        this.idUser = idUser;
        this.idEvent = idEvent;
        this.titreEvent = titreEvent;
        this.pseudoEcrivain = pseudoEcrivain;
        this.messageEvent = messageEvent;
        this.date = date;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public String getTitreEvent() {
        return titreEvent;
    }

    public String getPseudoEcrivain() {
        return pseudoEcrivain;
    }

    public String getMessageEvent() {
        return messageEvent;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public void setTitreEvent(String titreEvent) {
        this.titreEvent = titreEvent;
    }

    public void setPseudoEcrivain(String pseudoEcrivain) {
        this.pseudoEcrivain = pseudoEcrivain;
    }

    public void setMessageEvent(String messageEvent) {
        this.messageEvent = messageEvent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
