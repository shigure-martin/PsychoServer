package com.psychoServer.Automatic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Automatic {
    private static final String rootPath = "D:\\ECNU\\软件开发实践\\PsychoServer\\src\\main\\java\\com";
    private static final String rootControllerPath = "/Automatic/rootController.txt";
    private static final String rootServicePath = "/Automatic/rootService.txt";
    private static final String rootRepositoryPath = "/Automatic/rootRepository.txt";

    public static void main(String args[]) throws IOException {
        List<ClassDetail> classDetails = getAllClassDetails();
        for (ClassDetail classDetail : classDetails) {
            generate(classDetail);
        }
    }

    private static List<ClassDetail> getClassDetails() {
        List<ClassDetail> classDetails = new ArrayList<>();
        ClassDetail classDetail = new ClassDetail();
        classDetail.setClassName("AssessmentInfo");
        classDetail.setPathName("assessmentInfo");
        classDetail.setControllerName("评估信息");
        classDetails.add(classDetail);
        return classDetails;
    }

    private static List<ClassDetail> getAllClassDetails() throws IOException {
        List<ClassDetail> classDetails = new ArrayList<>();
        List<String> fileNames = getFile();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (String fileName : fileNames) {
            ClassDetail classDetail = new ClassDetail();
            String pathName = fileName.replaceAll("Info", "").toLowerCase();
            System.out.println(fileName + "中文名:");
            String controllerName = br.readLine();

            classDetail.setClassName(fileName);
            classDetail.setPathName(pathName);
            classDetail.setControllerName(controllerName);
            classDetails.add(classDetail);
        }
        return classDetails;
    }

    private static List<String> getFile() {
        String path = rootPath + "/entity";
        List<String> fileNames = new ArrayList<>();
        File file = new File(path);
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            String fileName = array[i].getName();
            if (!fileName.equals("IEntity.java") && !fileName.equals("Account.java")) {
                fileNames.add(fileName.replace(".java", ""));
            }
        }
        return fileNames;
    }

    private static void generate(ClassDetail classDetail) {
        generate(rootPath + rootRepositoryPath, "Repository", classDetail);
        generate(rootPath + rootServicePath, "Service", classDetail);
        generate(rootPath + rootControllerPath, "Controller", classDetail);
    }

    private static final String packageName = "";
    private static final String packageSign = "###";
    private static final String classSign = "@@@@";
    private static final String classSignSmall = "!!!";
    private static final String pathSign = ",,,";
    private static final String controllerSign = "&&&&";

    private static void generate(String readPath, String type, ClassDetail classDetail) {
        File rootControllerWrite = new File(rootPath + "/" + type.toLowerCase() + "/" + classDetail.getClassName() + type + ".java");
        try (FileReader reader = new FileReader(readPath);
             BufferedReader br = new BufferedReader(reader);
             FileWriter writer = new FileWriter(rootControllerWrite);
             BufferedWriter out = new BufferedWriter(writer)
        ) {
            rootControllerWrite.createNewFile();
            String line;
            while ((line = br.readLine()) != null) {
                out.write(line.replaceAll(packageSign, packageName).replaceAll(classSign, classDetail.getClassName()).replaceAll(classSignSmall, lowerFirstCapse(classDetail.getClassName()))
                        .replaceAll(pathSign, classDetail.getPathName()).replaceAll(controllerSign, classDetail.getControllerName()) + "\r\n");
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String lowerFirstCapse(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
