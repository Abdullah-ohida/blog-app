package com.blogger.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
@Service("cloudinary")
public class CloudinaryImpl implements CloudStorageService{

    Cloudinary cloudinary;

    public CloudinaryImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @Override
    public Map<?, ?> uploadImage(MultipartFile file, Map<?, ?> imageProperties) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), imageProperties);
    }

    public void uploadImage(File file, Map<?, ?> imageProperties) throws IOException{
        cloudinary.uploader().upload(file, imageProperties);
    }
}
