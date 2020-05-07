package com.hengxin.mall.model;

import java.io.Serializable;
import java.util.List;


public class TagModel implements Serializable {
    public List<Tag> history;
    public List<Tag> hot;

    public static class Tag {
        public int element_id;
        public String element_type;
        public String title;
        public String subtitle;
        public String extend;
    }
}
