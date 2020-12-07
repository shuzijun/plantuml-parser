package com.shuzijun.plantumlparser.core;

/**
 * 转换限定符
 *
 * @author shuzijun
 */
public class VisibilityUtils {

    public static String toCharacter(String visibility) {
        if (visibility == null) {
            return "+";
        } else if ("private".equals(visibility)) {
            return "-";
        } else if ("default".equals(visibility)) {
            return "~";
        } else if ("protected".equals(visibility)) {
            return "#";
        } else {
            return "+";
        }
    }
}
