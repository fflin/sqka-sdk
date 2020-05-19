package com.hengxin.pickimg.utils;

/**
 * author : fflin
 * date   : 2019/12/26 15:48
 * desc   : 使用鲁班库压缩图片后的回调
 * version: 1.0
 */
public interface CompressCallBack {
    void onCompressSucc(String filePath);
    void onCompressFiled(String message);
}
