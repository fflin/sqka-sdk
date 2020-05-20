package com.hengxin.mall.model;

import java.util.List;

/**
 * author: Y_Qing
 * date: 2019/1/4
 */
public class UpLoadFileModel {

    public List<FilesBean> files;


    public static class FilesBean {
        /**
         * fileName : 17187988704.jpg
         * filePath : E:\MyWork\sqka\sqka-server\app\public\files\17187988704.jpg
         * fileUrl : http://localhost:8101/public/files/17187988704.jpg
         */
        public String fileName;
        public String filePath;
        public String fileUrl;

    }
}
