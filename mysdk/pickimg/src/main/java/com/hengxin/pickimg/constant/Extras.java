package com.hengxin.pickimg.constant;

/**
 * author: Y_Qing
 * date: 2019/1/2
 */
public interface Extras {
    String albumTypeKey = "album_type_key";//在相册中区分是图片，还是相册集

    String albumWay = "album_way";//区分是相册还是拍照
    String cropOutputImageWidth = "cropOutputImageWidth";
    String cropOutputImageHeight = "cropOutputImageHeight";
    String photoFileSrc = "photo_src";
    String intputPath = "intPutPath";//返回裁剪图片路径
    String intfilePath = "intPutFilePath";
    String maxImageSize = "maxImageSize";//设置图片的最大值
    String transAlbumKey = "transAlbumKey";
}
