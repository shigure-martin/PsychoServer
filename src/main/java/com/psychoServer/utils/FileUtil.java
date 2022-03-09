package com.psychoServer.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static String FILE_PREFIX = "C:\\Users\\Administrator\\Desktop\\celebritiesGathering\\tmp\\";
    public static String FE_RESOURCES = "C:\\Users\\Administrator\\Desktop\\celebritiesGathering\\resources\\";

    {
        File file = new File(FILE_PREFIX);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void setUp(String filePrefix) {
        FILE_PREFIX = filePrefix;
        File file = new File(FILE_PREFIX);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static String uploadFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        return uploadFile(multipartFile, fileName);
    }

    public static String uploadFile(MultipartFile multipartFile, String fileName) {
        long current = System.currentTimeMillis();
        File parent = new File(FILE_PREFIX + current);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        File newFile = new File(parent, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
            outputStream.write(multipartFile.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return current + "/" + fileName;
    }

    public static String createFile(byte[] bytes, String postfix) throws IOException {
        return createFile(null, bytes, postfix);
    }

    public static String createFile(String parent, byte[] bytes, String postfix) throws IOException {
        final String fileName = "/" + postfix;
        Long time = System.currentTimeMillis();
        File parentFile;
        if (parent != null && parent.length() > 0) {
            parentFile = new File(FILE_PREFIX + parent);
        } else {
            parentFile = new File(FILE_PREFIX + time);
        }
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        File newFile = new File(parentFile, fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(newFile);
            outputStream.write(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Path from = Paths.get(FileUtil.FILE_PREFIX, time + fileName);
        Path to = Paths.get(FileUtil.FE_RESOURCES + time + fileName);
        Files.createDirectories(to);
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);

        return time + fileName;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 1024 * 1024];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static File getFileByPath(String filePath) {
        return new File(FILE_PREFIX + filePath);
    }


}
