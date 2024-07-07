package edu.qust.reggie.controller;

import edu.qust.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 
 * @version 1.0
 * @description 通用控制层（用于实现文件上传......）
 */

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.file.path}")
    private String path;

    /**
     * 处理文件上传的请求
     * @param file spring 框架封装的 MultipartFile 对象
     * @return 文件名称（后续页面展示需要使用文件名）
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        // UUID 随机生成文件名，避免重复
        String prefix = UUID.randomUUID().toString();
        // 截取原文件名的后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新的文件名
        String fileName = prefix + suffix;

        // 要存放的目录
        File dir = new File(path);
        // 判断目录是否存在
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }

    /**
     * 处理文件下载的请求（在浏览器回显图片 ）
     * @param name 文件名
     * @param response HttpServletResponse对象 用于在浏览器端写入数据
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // 获取输入流，通过输入流读取文件数据
            FileInputStream fileInputStream = new FileInputStream(new File(path + name));
            // 获取输出流，通过输出流将文件内容写回浏览器，用于展示
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            // 关闭资源
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
