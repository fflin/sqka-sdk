package com.hengxin.pickimg.model;

import java.io.Serializable;
import java.util.List;

public class AlbumInfo implements Serializable {

    public int imageID;
    public String pathAbsolute;
    public String pathFile;
    public String nameAlbum;
    public List<PhotoInfo> list;

    @Override
    public String toString() {
        return "AlbumInfo{" +
                "imageID=" + imageID +
                ", pathAbsolute='" + pathAbsolute + '\'' +
                ", pathFile='" + pathFile + '\'' +
                ", nameAlbum='" + nameAlbum + '\'' +
                ", list=" + list.size() +
                '}';
    }
}
