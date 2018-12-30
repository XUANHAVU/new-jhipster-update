package com.xuanhatlu.hihi.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void store(MultipartFile file);

    Resource loadFile(String filename);

    void deleteAll();

    void init();
}
