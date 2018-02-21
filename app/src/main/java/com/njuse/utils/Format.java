package com.njuse.utils;

/**
 * Created by Administrator on 2018/2/21.
 */

/*
    用于作字符串匹配之用
 */

public class Format {
    public static boolean isEmailLegal(String email) {
        /*
           设定邮箱地址的合法规则，合法邮箱地址要求如下：
                   （1）字符必须是英文或数字开始
                   （2）必须包含一个@
                   （3）@符号在. 符号前面
                   （4）以英文或数字结尾
		 */
        //设置一个正则表达式
        String reg = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        //告知此字符串是否匹配给定的正则表达式。
        if (email.matches(reg))
            return true;
        else
            return false;
    }

    public static boolean isPhoneNumberLegal(String phoneNumber){
        String phoneNumPattern = "(13|14|15|18)[0-9]{9}";     //手机号正则表达式
        if (phoneNumber.matches(phoneNumPattern)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPasswordLegal(String password){
        String passwordPattern = "[A-Za-z0-9_]{8,16}";      //密码匹配的正则表达式(还未完善)
        if (password.matches(passwordPattern)) {
            return true;
        } else {
            return false;
        }
    }
}
