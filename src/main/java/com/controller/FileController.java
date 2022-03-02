package com.controller;

import com.response.BaseResponse;
import com.response.SuccessResponse;
import com.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/file")
@Api(value = "/api/file", tags = "文件相关接口")
public class FileController extends BaseController {
    /**
     * 新建
     *
     * @throws IOException
     */
    @ApiOperation(value = "上传文件")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse create(@RequestParam MultipartFile file) throws IOException {
        String relativePath = FileUtil.uploadFile(file);

        Path from = Paths.get(FileUtil.FILE_PREFIX, relativePath);
        Path to = Paths.get(FileUtil.FE_RESOURCES + relativePath);
        Files.createDirectories(to);
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        return new SuccessResponse<>(relativePath);
    }
}
