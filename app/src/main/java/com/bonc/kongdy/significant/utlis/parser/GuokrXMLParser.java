package com.bonc.kongdy.significant.utlis.parser;

import com.orhanobut.logger.Logger;

/**
 * 果壳网科学栏目网页分割
 * 使用：new对象时传入html字符串后，通过调用getEndStr获取截取后内容
 */
public class GuokrXMLParser {

    private static String STR_CUT_LEFT="<div class=\"container article-page\">";//分割左边界
    private static String STR_CUT_RIGHT="<div class=\"recommend-articles\">";//分割右边界
    private String parseStr=null;//待解析字符串

    public String getEndStr() {
        return endStr;
    }

    private String endStr=null;//已解析字符串
    public GuokrXMLParser(String parseStr){
        this.parseStr=parseStr;
        parseData();
    }

    /**
    *解析数据，并把解析得到的最终的字符串放入endStr中
    */
    private void parseData() {
        String first_cut[]=parseStr.split(STR_CUT_LEFT);
        if(first_cut.length>1){
            String second_cut[]=first_cut[1].split(STR_CUT_RIGHT);
            if(second_cut.length>1){
                endStr=STR_CUT_LEFT+second_cut[0]+"</div>";
            }else{
                Logger.e("ERROR", "第二次解析出错，未找到有效分割元素");
            }
        }else{
            Logger.e("ERROR","第一次解析出错，未找到有效分割元素");
        }
    }
}
