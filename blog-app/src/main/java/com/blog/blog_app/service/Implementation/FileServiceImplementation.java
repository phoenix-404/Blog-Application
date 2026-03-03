package com.blog.blog_app.service.Implementation;

import com.blog.blog_app.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

//    uploadImage handles putting the file on the server
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //1.Get original File Name (e.g. myphoto.png)
        String name = file.getOriginalFilename();

        //2. Generate a random name to avoid name conflicts
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

        //3. Create full path
        String filePath = path + File.separator + fileName1;

        //4. Create folder if not created
        File f = new File(path);
        if(!f.exists())
            f.mkdir();

        //5. Copy the file to the destination
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

//    getResource is used for the opposite: getting the file off the server to show it to the user.

//    http://localhost:9090/images/myphoto.jpg, you will get a 404 error.
//    getResource (or a "Serve Image" method) acts as a bridge. It:
//    1. Locates the physical file on your hard drive using the path and fileName.
//    2. Converts that file into an InputStream (a stream of bytes).
//    3. Allows your Controller to "stream" those bytes back to the browser so the image can be displayed in an <img> tag or a mobile app.

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator +fileName;
        InputStream is = new FileInputStream(fullPath);
        // This Stream now contains the raw data of our Image
        return is;
    }
}
