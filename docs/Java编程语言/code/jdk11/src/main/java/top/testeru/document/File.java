package top.testeru.document;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
    public static void main(String[] args) {
        try {
            //文件读取
            Path inputPath = Paths.get("input.txt");
            byte[] fileBytes = Files.readAllBytes(inputPath);

            //文件写入
            Path outputPath = Paths.get("output.txt");
            Files.write(outputPath, fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
