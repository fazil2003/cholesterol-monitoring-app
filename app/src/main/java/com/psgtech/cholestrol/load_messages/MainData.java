package com.psgtech.cholestrol.load_messages;

public class MainData {
    private String messageID;
    private String messageDate;
    private String messageDoctorID;
    private String messagePatientID;

    public String getMessageDoctorID() {
        return messageDoctorID;
    }

    public void setMessageDoctorID(String messageDoctorID) {
        this.messageDoctorID = messageDoctorID;
    }

    public String getMessagePatientID() {
        return messagePatientID;
    }

    public void setMessagePatientID(String messagePatientID) {
        this.messagePatientID = messagePatientID;
    }

    private String messageQuery;
    private String messageResponse;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageQuery() {
        return messageQuery;
    }

    public void setMessageQuery(String messageQuery) {
        this.messageQuery = messageQuery;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }
}
