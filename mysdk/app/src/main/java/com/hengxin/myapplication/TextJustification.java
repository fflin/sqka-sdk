package com.hengxin.myapplication;

/**
 * author : fflin
 * date   : 2020/5/22 15:31
 * desc   :
 * version: 1.0
 */

import java.util.ArrayList;

import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class TextJustification {

    public static void justify(TextView textView, float contentWidth) {
        String text = textView.getText().toString();
        String tempText;
        String resultText = "";
        Paint paint = textView.getPaint();

        ArrayList<String> paraList = new ArrayList<String>();
        paraList = paraBreak(text, textView);
        for (int i = 0; i < paraList.size(); i++) {
            ArrayList<String> lineList = lineBreak(paraList.get(i).trim(), paint, contentWidth);
            tempText = TextUtils.join(" ", lineList).replaceFirst("\\s*", "");
            resultText += tempText.replaceFirst("\\s*", "") + "\n";
        }

        textView.setText(resultText);
    }

    //分开每个段落
    public static ArrayList<String> paraBreak(String text, TextView textview) {
        ArrayList<String> paraList = new ArrayList<String>();
        String[] paraArray = text.split("\\n+");
        for (String para : paraArray) {
            paraList.add(para);
        }
        return paraList;
    }

    //分开每一行，使每一行填入最多的单词数
    private static ArrayList<String> lineBreak(String text, Paint paint, float contentWidth) {
        String[] wordArray = text.split("\\s");
        Log.i("fflin","length = "+wordArray.length);
        ArrayList<String> lineList = new ArrayList<String>();
        String myText = "";

        for (String word : wordArray) {
            if (paint.measureText(myText + " " + word) <= contentWidth) {
                Log.i("fflin","-------------- if");
                myText = myText + " " + word;
            }
            else {
                Log.i("fflin","-------------- else ");
                int totalSpacesToInsert = (int) ((contentWidth - paint.measureText(myText)) / paint.measureText(" "));
                Log.i("fflin","-------------- totalSpacesToInsert "+totalSpacesToInsert);
                lineList.add(justifyLine(myText, totalSpacesToInsert));
                myText = word;

            }
        }
        lineList.add(myText);
        return lineList;
    }

    //已填入最多单词数的一行，插入对应的空格数直到该行满  导致anr
    private static String justifyLine(String text, int totalSpacesToInsert) {
        if (totalSpacesToInsert == 0) return text;
        String[] wordArray = text.split("\\s");
        String toAppend = " ";

        while ((totalSpacesToInsert) >= (wordArray.length - 1)) {
            toAppend = toAppend + " ";
            totalSpacesToInsert = totalSpacesToInsert - (wordArray.length - 1);
        }
        int i = 0;
        String justifiedText = "";
        for (String word : wordArray) {
            if (i < totalSpacesToInsert)
                justifiedText = justifiedText + word + " " + toAppend;

            else
                justifiedText = justifiedText + word + toAppend;

            i++;
        }

        return justifiedText;
    }


}
