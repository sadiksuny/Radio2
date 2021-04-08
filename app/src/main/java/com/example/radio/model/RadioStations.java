package com.example.radio.model;

public class RadioStations {
    private static String[] stringList;

    public static RadioStation[] radioStationsArray = {
            new RadioStation("http://stream.whus.org:8000/whusfm", "UConn", "https://whus.org/wp-content/uploads/2015/01/whusheader.png"),
            new RadioStation("https://playerservices.streamtheworld.com/api/livestream-redirect/WNPRFM.mp3?dist=onlineradiobox", "NPR", "https://media.npr.org/assets/img/2019/06/17/nprlogo_rgb_whiteborder_custom-7c06f2837fb5d2e65e44de702968d1fdce0ce748-s800-c85.png"),
            new RadioStation("https://crystalout.surfernetwork.com:8001/WBMW_MP3", "106.5", "https://www.radio.net/images/broadcasts/f9/a7/21401/c300.png")

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

    public static String[] getRadioImageLinks(){
        stringList= new String[radioStationsArray.length];

        for(int i=0; i<radioStationsArray.length; i++){
            stringList[i]= radioStationsArray[i].getRadioImageLink();
        }
        return  stringList;
    }
}
