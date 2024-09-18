package com.uwu.study.file;

import cn.hutool.json.JSON;
import com.alibaba.druid.support.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/file")
public class OperateLocalFileController {

    @RequestMapping("/operate")
    public String operate() {
        String sourceFilePath = "/Users/wangpeng/test.txt"; // 输入文件路径
        String outputFilePath = "/Users/wangpeng/output.txt"; // 输出文件路径

        String processedLines = processFile(sourceFilePath);

        // 写入新文件
        writeFile(outputFilePath, processedLines);
        return "ok";
    }


    private String processFile(String filePath){
        // 读取文件并处理
        Path path = Paths.get(filePath);
        try {
            List<String> lines = Files.readAllLines(path);
            // 在这里进行数据整理，例如去掉空行，转换为大写等
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    sb.append(line);
                }
            }
            String processedLines = sb.toString();
            System.out.println(processedLines);
            Map<String, Map<String, List<String>>> parse = (Map<String, Map<String, List<String>>>) JSONUtils.parse(processedLines);
            parse.forEach((k, v) -> {
                System.out.println(k);
                v.forEach((k1, v1) -> {
                    System.out.println(k1);
                    System.out.println(Arrays.toString(v1.toArray()));
                }

                );
            });
            return processedLines;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void writeFile(String filePath, String lines)  {
        // 写入新文件
        Path path = Paths.get(filePath);
        try {
            Files.write(path, Collections.singletonList(lines));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
