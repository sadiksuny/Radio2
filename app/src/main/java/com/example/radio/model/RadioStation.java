package com.example.radio.model;

import android.media.Image;

import com.example.radio.R;

public class RadioStation {
    private String streamLink;
    private String radioName;
    //private Image radioImage;
    public RadioStation(){
        this.streamLink="http://stream.whus.org:8000/whusfm";
        //this.streamLink="https://d1qg6pckcqcdk0.cloudfront.net/classical2/kuijkenveronica_wamozartsonatas_02_sonatafor2pianosindm.m4a";
        this.radioName="UConn";


    }
    public RadioStation(String streamLink, String radioName){
        this.streamLink= streamLink;
        this.radioName= radioName;
    }

    public String getStreamLink() {
        return streamLink;
    }

    public void setStreamLink(String streamLink) {
        this.streamLink = streamLink;
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }
}
