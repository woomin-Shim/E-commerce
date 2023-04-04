package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.domain.ItemImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    //파일 경로명
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    //파일 경로명(스토리지)에 사진 저장
    public List<ItemImage> storeImages(List<MultipartFile> multipartFiles) throws IOException {
        List<ItemImage> storeResult = new ArrayList<>();
        for (MultipartFile multipartfile : multipartFiles) {
            storeResult.add(storeImage(multipartfile));
        }
        return storeResult;
    }

    public ItemImage storeImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        // ex) image1.jpeg
        String oriImageName = multipartFile.getOriginalFilename();

        //서버에 저장될 파일명
        String storeImageName = createStoreImageName(oriImageName);

        //스토리지에 저장
        multipartFile.transferTo(new File(getFullPath(storeImageName)));

        return ItemImage.builder()
                .originalName(oriImageName)
                .storeName(storeImageName)
                .build();
    }

    private String createStoreImageName(String oriImageName) {
        String ext = extractExt(oriImageName);  //jpeg
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자 추출
    private String extractExt(String oriImageName) {
        int pos = oriImageName.lastIndexOf(".");
        return oriImageName.substring(pos + 1);
    }
}
