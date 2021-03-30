package com.example.radio.model;

public class RadioStations {
    private static String[] stringList;

    public static RadioStation[] radioStationsArray = {
            new RadioStation("http://stream.whus.org:8000/whusfm", "UConn"),
            new RadioStation("https://playerservices.streamtheworld.com/api/livestream-redirect/WNPRFM.mp3?dist=onlineradiobox", "NPR"),
            new RadioStation("https://crystalout.surfernetwork.com:8001/WBMW_MP3", "106.5")

    };

    public static String[] getRadioLinks() {
        stringList = new String[radioStationsArray.length];

        for (int i = 0; i < radioStationsArray.length; i++) {
            stringList[i] = radioStationsArray[i].getStreamLink();
        }
        return stringList;
    }

    public static String[] getRadioNames(){
        stringList= new String[radioStationsArray.length];

        for(int i = 0; i< radioStationsArray.length; i++){
            stringList[i]= radioStationsArray[i].getRadioName();
        }
        return stringList;
    }
}
