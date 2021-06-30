package hcb.gad.youtube_counterfeit.videos.controller;


import com.google.common.io.Files;
import hcb.gad.youtube_counterfeit.videos.payload.UploadFileDTO;
import hcb.gad.youtube_counterfeit.videos.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/videoss")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);


    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileDTO uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("typeid") long typeid,@RequestParam("userid") long userid) {

        UUID id = UUID.randomUUID();

        String fileName = fileStorageService.storeFile(file, Files.getFileExtension(file.getOriginalFilename()));

        String extension = Files.getFileExtension(fileName);

//        fileStorageService.mediatypes(extension);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(String.valueOf(id))
                .toUriString();

        fileStorageService.create(id, fileName,fileDownloadUri,extension,file.getSize(),userid,typeid);

        return new UploadFileDTO(id,fileName, fileDownloadUri,extension, file.getSize(),userid,typeid);
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<List<UploadFileDTO>> getAllVideoss() {
        return ResponseEntity.ok(fileStorageService.findAll());
    }
    

    @DeleteMapping
    public ResponseEntity<Void> deleteall() {
        fileStorageService.deleteall();
        return ResponseEntity.noContent().build();
    }

}
