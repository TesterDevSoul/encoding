package top.testeru.document;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStream {
    public static void main(String[] args) {
        //文件的读取
//        try {
//            FileInputStream inputStream = new FileInputStream("input.txt");
//            byte[] buffer = new byte[1024];
//            int len = inputStream.read(buffer);
//            String fileContent = new String(buffer, 0, len, "UTF-8");
//            System.out.println(fileContent);
//            inputStream.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        //文件的写入
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("file.txt");
            String data = "Hello, world!";
            byte[] bytes = data.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        //文件边读边写
//        FileInputStream inputStream = null;
//        FileOutputStream outputStream = null;
//
//        try {
//            inputStream = new FileInputStream("input.txt");
//            outputStream = new FileOutputStream("output.txt");
//
//            int byteRead;
//            while ((byteRead = inputStream.read()) != -1) {
//                outputStream.write(byteRead);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
