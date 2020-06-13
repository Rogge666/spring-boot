package com.rogge.test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2018/8/28
 * @since 1.0.0
 */
public class ParamMain {
    //pom物理地址
    private static final String inFilePath = "C:\\Users\\Administrator\\Desktop\\银联\\S24_CODES20181112.txt";
    //生成的Maven文件地址
    private static final String outFilePath = "C:\\Users\\Administrator\\Desktop\\out.txt";

    private static final String inputParam = "OCCUP";

    public static void main(String[] args) throws Exception {
        File mavenTxtFile = new File(outFilePath);
        if(!mavenTxtFile.exists()){
            mavenTxtFile.createNewFile();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mavenTxtFile), "UTF-8"));

        File file = new File(inFilePath);
        InputStream is = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")));
        String line;
        Map<String,String> map = new HashMap<>();
        while ((line = br.readLine()) != null) {
            try {
                String[] splitLine = line.split("\\|");
                if(inputParam.equals(splitLine[1].trim())){
                    map.put(splitLine[2],splitLine[4]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writeFile(writer,map);
        writer.flush();
        writer.close();
    }

    private static void writeFile(Writer writer, Map<String,String> map) throws IOException {
        Map<String,String> newMap = sortMapByKey(map);
        Set<String> lSet = newMap.keySet();
        for (String lS : lSet) {
            StringBuilder sb = new StringBuilder();
            sb.append(lS)
                    .append("\t")
                    .append(newMap.get(lS))
                    .append("\r");
            System.out.println(sb.toString().trim());
        }
//        writer.write(sb.toString());
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

}
