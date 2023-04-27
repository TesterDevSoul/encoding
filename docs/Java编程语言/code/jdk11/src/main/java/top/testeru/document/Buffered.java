package top.testeru.document;

import java.io.*;

public class Buffered {


    public static void main(String[] args) {
        //文件的读取
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
//            System.out.println(reader.readLine());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        //文件的写入
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
//            String line = "文件写入";
//            writer.write(line);
//            writer.newLine();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        //文件边读边写
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            writer = new BufferedWriter(new FileWriter("output.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
