package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value="/images")
    public ResponseEntity<?> imageUpload (@RequestParam("id") Long id,
                                          @RequestParam("name") String name,
                                          @RequestParam ("file")MultipartFile file){
        try{
            imageService.imageStore(file, id);
        }catch (IOException e){
            return new ResponseEntity<>("Error de archivo", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new String[]{id.toString(),name}, HttpStatus.OK);
    }


    @GetMapping(value = "/download/{name}")
    public  ResponseEntity<Resource> getImage(@PathVariable("name")String name){
        Path targetPath = Paths.get("./images/"+name).normalize();
        try {
            Resource resource = new UrlResource(targetPath.toUri());
            if(resource.exists()){
                String contentType = Files.probeContentType(targetPath);
                return ResponseEntity.ok().contentType(new MediaType(MediaType.parseMediaType(contentType))).body(resource);
            }
        }catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }
}


