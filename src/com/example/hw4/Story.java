package com.example.hw4;

import java.io.Serializable;

class Story implements Serializable {
    private String storyID;
    private String title;
    private String publicationDate;
    private String imageURL;
    private String audioURL;
    private String textURL;
    private String teaser;
    private String reporterName;
    private String dateAired;
    private String lengthOfBroadcast;

    public static String convertDurationFromSeconds(String seconds) {
        int startSeconds = Integer.parseInt(seconds);

        int minutes = startSeconds / 60;
        startSeconds = startSeconds % 60;


        return minutes + " min " + startSeconds + " sec";
    }

    public Story() {

    }



    public Story(String storyID, String title, String publicationDate,
        String imageURL, String audioURL, String teaser,
        String reporterName, String dateAired, String lengthOfBroadcast) {
        super();
        this.storyID = storyID;
        this.title = title;
        this.publicationDate = publicationDate;
        this.imageURL = imageURL;
        this.audioURL = audioURL;
        this.textURL = textURL;
        this.teaser = teaser;
        this.reporterName = reporterName;
        this.dateAired = dateAired;
        this.lengthOfBroadcast = lengthOfBroadcast;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }




    public String getTextURL() {
        return textURL;
    }

    public void setTextURL(String textURL) {
        this.textURL = textURL;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getDateAired() {
        return dateAired;
    }

    public void setDateAired(String dateAired) {
        this.dateAired = dateAired;
    }

    public String getLengthOfBroadcast() {
        return lengthOfBroadcast;
    }

    public void setLengthOfBroadcast(String lengthOfBroadcast) {
        this.lengthOfBroadcast = lengthOfBroadcast;
    }


    public String getAudioURL() {
        return audioURL;
    }


    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }




    public String getStoryID() {
        return storyID;
    }




    public void setStoryID(String storyID) {
        this.storyID = storyID;
    }




    @Override
    
    public String toString() {
        return this.title;
    }




}