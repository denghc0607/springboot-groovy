//package com.denghc.springboot.groovy.utils;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.Stack;
//
///**
// * @ClassName: ScriptUtils.java
// * @author: denghc
// * @date: 2018年5月23日-上午9:23:47
// */
//public class ScriptUtils {
//
//    private static final String YYYYMMDD = "yyyy-MM-dd";
//
//    private static int compare(String str, String target) {
//        int d[][]; // 矩阵
//        int n = str.length();
//        int m = target.length();
//        int i; // 遍历str的
//        int j; // 遍历target的
//        char ch1; // str的
//        char ch2; // target的
//        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
//        if (n == 0) {
//            return m;
//        }
//        if (m == 0) {
//            return n;
//        }
//        d = new int[n + 1][m + 1];
//        for (i = 0; i <= n; i++) { // 初始化第一列
//            d[i][0] = i;
//        }
//        for (j = 0; j <= m; j++) { // 初始化第一行
//            d[0][j] = j;
//        }
//        for (i = 1; i <= n; i++) { // 遍历str
//            ch1 = str.charAt(i - 1);
//            // 去匹配target
//            for (j = 1; j <= m; j++) {
//                ch2 = target.charAt(j - 1);
//                if (ch1 == ch2) {
//                    temp = 0;
//                } else {
//                    temp = 1;
//                }
//                // 左边+1,上边+1, 左上角+temp取最小
//                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
//            }
//        }
//        return d[n][m];
//    }
//
//    private static int min(int one, int two, int three) {
//        return (one = one < two ? one : two) < three ? one : three;
//    }
//
//    /**
//     * @desc: 获取两字符串的相似度
//     * @author: denghc
//     * @date: 2018年5月23日-下午1:25:12
//     */
//    public static double getSimilarityRatio(String str, String target) {
//        return 1 - (double) compare(str, target) / Math.max(str.length(), target.length());
//    }
//
//    public static final String[] CHINESE_VALUES_SHOW =
//            {"零", "一", "二", "两", "三", "四", "五", "六", "七", "八", "九", "点", "十", "百", "千", "万", "亿"};
//    public static final String[] BLANKCHARS =
//            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
//
//    /**
//     * @desc: 汉字表达的字符串转换为阿拉伯数字数值
//     * @author: denghc
//     * @date: 2018年5月23日-上午11:02:31
//     */
//    public static double chineseToNum(String str) {
//        String string = StringUtils.replaceEach(str, new String[]{"两"}, new String[]{"二"});
//        String cval = "零一二三四五六七八九";
//        String uval = "点十百千万亿";
//        double[] unum = {0.1, 10, 100, 1000, 10000, 100000000};
//        double num = 0;
//        char[] arr = string.toCharArray();
//        int len = arr.length;
//        int pointcount = 0;
//        Stack<Double> stack = new Stack<>();
//        for (int i = 0; i < len; i++) {
//            char s = arr[i];
//            if ('零' == s) {
//                continue;
//            }
//            int index = uval.indexOf(s);
//            if (-1 == index) {
//                double temp = (double) cval.indexOf(s);
//                if (!stack.isEmpty() && stack.peek() < 1) {
//                    if (0 == pointcount) {
//                        temp = temp * stack.pop();
//                    } else {
//                        temp = stack.pop() + Math.pow(0.1, pointcount + 1) * temp;
//                    }
//                    stack.push(temp);
//                    pointcount++;
//                } else {
//                    stack.push(temp);
//                }
//            } else if (0 == index) {
//                stack.push(0.1);
//            } else {
//                double temp = 0;
//                double val = unum[index];
//                if (stack.isEmpty()) {
//                    stack.push(val);
//                    continue;
//                }
//                while (!stack.isEmpty() && stack.peek() < val) {
//                    temp += stack.pop();
//                }
//                if (0 == temp) {
//                    stack.push(val);
//                } else {
//                    stack.push(temp * val);
//                }
//            }
//        }
//        while (!stack.isEmpty()) {
//            num += stack.pop();
//        }
//        return num;
//    }
//
//}
//
