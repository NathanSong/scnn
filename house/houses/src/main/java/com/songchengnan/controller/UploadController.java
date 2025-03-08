package com.songchengnan.controller;

import com.songchengnan.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080") // 替换为你的前端地址
public class UploadController {

    //本地上传
    @PostMapping("/upload")
    public Result upload(@RequestParam("image") MultipartFile image) throws IOException {
        log.info("文件上传,参数:{}",image);

        String originalFilename = image.getOriginalFilename();
        // 构造唯一文件名
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString() + extname;
        log.info("新文件名:{}",newFileName);
        String path = "D:\\BiShe\\BiShe-house-front\\house-master\\public\\images\\"+newFileName;
        // 保存文件
        image.transferTo(new File(path));
        return Result.success(newFileName);
    }

}