package com.hengxin.myapplication;

import android.text.TextPaint;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * author : fflin
 * date   : 2020/5/22 16:04
 * desc   :
 * version: 1.0
 */
public class TextHelper {

    public static void justify(TextView textView, float contentWidth) {
        String text = textView.getText().toString().trim();
        String tempText;
        String resultText = "";
        TextPaint paint = textView.getPaint();
        //分段
        ArrayList<String> parseList = parseBreak(text,textView);
        for (int i = 0; i < parseList.size(); i++) {
            //段分行
            ArrayList<String> lineStr = lineBreak(parseList.get(i).trim(),paint, contentWidth);
            // 计算行宽可容纳的最多文字,插入空格
        }
    }

    // 段分行
    private static ArrayList<String> lineBreak(String trim, TextPaint paint, float contentWidth) {
        return null;
    }

    //将文字分段
    private static ArrayList<String> parseBreak(String text, TextView textView) {
        ArrayList<String> list = new ArrayList<>();
        String[] split = text.split("\\n+");
        for (String para : split) {
            list.add(para);
        }
        return list;
    }
}
