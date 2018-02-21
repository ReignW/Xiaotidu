package com.njuse.utils;

/**
 * Created by Administrator on 2018/2/20.
 */

public class IdentifyingCodeStyle {
    public static String getCodeStyle(String identifyingCode){
        String codeStyle =
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                        "<tbody><tr>\n" +
                        "<td bgcolor=\"#f7f9fa\" align=\"center\" style=\"padding:22px 0 20px 0\" class=\"responsive-table\">\n" +
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:f7f9fa; border-radius:3px;border:1px solid #dedede;margin:0 auto; background-color:#ffffff\" width=\"552\" class=\"responsive-table\">\n" +
                        "<tbody><tr>\n" +
                        "<td bgcolor=\"#0373d6\" height=\"54\" align=\"center\" style=\"border-top-left-radius:3px;border-top-right-radius:3px;\">\n" +
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                        "<tbody><tr>\n" +
                        "<td align=\"center\" class=\"zhwd-high-res-img-wrap zhwd-zhihu-logo\" style=\"font-size: 30px; color:#ffffff; \">小题督\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</tbody></table>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0 15px 0px 15px;\">\n" +
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"480\" class=\"responsive-table\">\n" +
                        "<tbody><tr>\n" +
                        "<td>\n" +
                        "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "<tbody><tr>\n" +
                        "<td>\n" +
                        "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\" class=\"responsive-table\">\n" +
                        "<tbody><tr>\n" +
                        "<td width=\"550\" align=\"left\" valign=\"top\">\n" +
                        "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "\n" +
                        "<tbody><tr>\n" +
                        "<td bgcolor=\"#ffffff\" align=\"left\" style=\"background-color:#ffffff; font-size: 17px; color:#7b7b7b; padding:28px 0 0 0;line-height:25px;\"><b>亲爱的用户，你好，</b>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "\n" +
                        "<td align=\"left\" valign=\"top\" style=\"font-size:14px; color:#7b7b7b; line-height: 25px; font-family:Hiragino Sans GB; padding: 20px 0px 20px 0px\">你正在请求验证邮箱，请在 30 分钟内输入以下6位数字完成验证。 如非你本人操作，请忽略此邮件。\n" +
                        "\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"border-bottom:1px #f1f4f6 solid; padding: 0 0 40px 0;\" align=\"center\" class=\"padding\">\n" +
                        "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"responsive-table\">\n" +
                        "<tbody><tr>\n" +
                        "<td>\n" +
                        "<span style=\"font-family:Hiragino Sans GB;\"><div style=\"padding:10px 18px 10px 18px;border-radius:3px;text-align:center;text-decoration:none;background-color:#ecf4fb;color:#4581E9;font-size:20px; font-weight:700; letter-spacing:2px; margin:0;white-space:nowrap\">" + identifyingCode + "\n" +
                        "</div>\n" +
                        "</span>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</tbody></table>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "\n" +
                        "</tbody></table>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</tbody></table>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</tbody></table>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</tbody></table>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</tbody></table>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</tbody></table>";

        return codeStyle;
    }
}
