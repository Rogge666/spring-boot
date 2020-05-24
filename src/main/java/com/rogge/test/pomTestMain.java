package com.rogge.test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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
public class pomTestMain {
    //pom物理地址
    private static final String pomPath = "E:\\idea_work_place\\collBatchServer\\pom.xml";
    //maven repo地址
    private static final String repoPath = "C:\\Users\\Administrator\\Desktop\\apache-maven-3.2.3\\";
    //生成的Maven文件地址
    private static final String mavenTxtPath = "C:\\Users\\Administrator\\Desktop\\maven.txt";

    public static void main(String[] args) throws Exception {
        File mavenTxtFile = new File(mavenTxtPath);
        if(!mavenTxtFile.exists()){
            mavenTxtFile.createNewFile();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mavenTxtFile), "UTF-8"));

        Map<String,String> properties = new HashMap<>();
        File file = new File(pomPath);
        InputStream is = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        boolean propertiesReading = false;
        boolean dependencyReading = false;
        int dependencyIndex = 0;

        String lGroupId = "";
        String lArtifactId = "";
        String lVersion = "";
        while ((line = br.readLine()) != null) {
            try {
                //加入properties属性
                if(propertiesReading && !line.contains("</properties>")){
                    takeProps(line,properties);
                }
                if(line.contains("properties>")){
                    propertiesReading = true;
                }
                if(line.contains("</properties>")){
                    propertiesReading = false;
                }

                //读dependency
                //加入properties属性
                if(dependencyReading && !line.contains("</dependency>")){
                    dependencyIndex++;
                    int endIndex1 = line.lastIndexOf("<");
                    int startIndex2 = line.indexOf(">");
                    if(dependencyIndex == 1){
                        lGroupId = line.substring(startIndex2+1,endIndex1);
                    }else if (dependencyIndex == 2){
                        lArtifactId = line.substring(startIndex2+1,endIndex1);
                    }else if (dependencyIndex == 3){
                        String version = line.substring(startIndex2+1,endIndex1);
                        if(version.startsWith("${")){
                            version = version.replace("${","").replace("}","");
                            version = properties.get(version);
                        }
                        lVersion = version;
                        dependencyIndex=0;
                        dependencyReading = false;
                        writeFile(writer,lGroupId,lArtifactId,lVersion);
                        lGroupId = "";
                        lArtifactId = "";
                        lVersion = "";
                    }
                }
                if(line.contains("<dependency>")){
                    dependencyReading = true;
                }
                if(line.contains("</dependency>")){
                    dependencyReading = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        writer.flush();
        writer.close();

    }

    private static void writeFile(Writer writer, String lGroupId, String lArtifactId, String lVersion) throws IOException {
        //mvn install:install-file -DgroupId=fbcds -DartifactId=fbcds -Dversion=1.0 -Dpackaging=jar -Dfile=fbcds-1.0.jar
        String jarFolderPath = repoPath+lGroupId.replace(".","\\")+"\\"+lArtifactId+"\\"+lVersion+"\\";
        String jarName = null;
        File file = new File(jarFolderPath);
        if(file.exists()){
            String[] fileNames = file.list();
            for (String fileName : fileNames) {
                if(fileName.endsWith(".jar")){
                    jarName = fileName;
                }
            }
            if(jarName == null){
                writer.write("找不到对应的Jar包======="+lGroupId+"====="+lArtifactId+"======="+lVersion+"\n");
                return;
            }
        }else{
            writer.write("找不到对应的Jar包======="+lGroupId+"====="+lArtifactId+"======="+lVersion+"\n");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("mvn install:install-file -DgroupId=")
                .append(lGroupId)
                .append(" -DartifactId=")
                .append(lArtifactId)
                .append(" -Dversion=")
                .append(lVersion)
                .append(" -Dpackaging=jar -Dfile=")
                .append(jarFolderPath)
                .append(jarName)
                .append("\r\n");
        writer.write(sb.toString());
    }

    private static void takeProps(String line,Map<String,String> properties){
        int startIndex1 = line.indexOf("<");
        int endIndex1 = line.lastIndexOf("<");
        int startIndex2 = line.indexOf(">");
        String key = line.substring(startIndex1+1,startIndex2);
        String value = line.substring(startIndex2+1,endIndex1);
        properties.put(key,value);
        System.out.println(key+"======"+value);
    }
}
