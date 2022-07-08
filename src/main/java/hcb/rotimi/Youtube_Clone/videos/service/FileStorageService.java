package hcb.rotimi.Youtube_Clone.videos.service;

import hcb.rotimi.Youtube_Clone.users.Users;
import hcb.rotimi.Youtube_Clone.users.UsersRepository;
import hcb.rotimi.Youtube_Clone.video_type.VideoType;
import hcb.rotimi.Youtube_Clone.video_type.VideoTypeRepository;
import hcb.rotimi.Youtube_Clone.videos.Videos;
import hcb.rotimi.Youtube_Clone.videos.VideosRepository;
import hcb.rotimi.Youtube_Clone.videos.payload.UploadFileDTO;
import hcb.rotimi.Youtube_Clone.videos.property.FileStorageProperties;
import hcb.rotimi.Youtube_Clone.videos.exception.*;

import hcb.rotimi.Youtube_Clone.videos.exception.FileStorageException;
import hcb.rotimi.Youtube_Clone.videos.exception.MyFileNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    private final VideosRepository videosRepository;
    private final Path fileStorageLocation;
    private final UsersRepository usersRepository;
    private final VideoTypeRepository videoTypeRepository;

    @Autowired
    public FileStorageService(VideosRepository videosRepository, FileStorageProperties fileStorageProperties, UsersRepository usersRepository, VideoTypeRepository videoTypeRepository) {
        this.videosRepository = videosRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.usersRepository = usersRepository;
        this.videoTypeRepository = videoTypeRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

//    public void mediatypes(String extension){
//
//        String[] mediatypes = {"mp4","mov","avi","mkv","webm","html5","mpeg-2","swf"};
//        Boolean correctextension = false;
//        try {
//            for (String word : mediatypes){
//                if (extension == word){
//                    correctextension = true;
//                }
//            }
//            if (!correctextension){
//                throw new FileUploadException("Sorry! Filename contains invalid path sequence");
//            }
//        } catch (FileStorageException | FileUploadException e) {
//            e.printStackTrace();
//        }
//    }

    public String storeFile(MultipartFile file, String extension) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Boolean correctextension = false;

        String[] mediatypes = {"mp4","mov","avi","mkv","webm","html5","mpeg-2","swf","mp3"};

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            System.out.println(extension);

            for (String word : mediatypes){
                if (extension.equals(word)){
                    correctextension = true;
                }

            }
            System.out.println(extension);
            if (!correctextension){
                throw new FileUploadException("Sorry, extension is not accepted");
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException | FileUploadException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<UploadFileDTO> findAll() {
        return videosRepository.findAll()
                .stream()
                .map(videos -> mapToDTO(videos, new UploadFileDTO()))
                .collect(Collectors.toList());
    }

    public void create(UUID id,String fileName, String fileDownloadUri, String fileType, long size, long user, long type ) {
        final Videos videos = new Videos();
        mapToEntity(id,fileName,user,type, videos, fileDownloadUri,fileType, size);
        videosRepository.save(videos);
    }

    public void deletebyid(final Long id) {
        videosRepository.deleteById(id);
    }

    public void deleteall() {
        videosRepository.deleteAll();
    }

    private UploadFileDTO mapToDTO(final Videos videos, final UploadFileDTO videosDTO) {
        videosDTO.setId(videos.getId());
        videosDTO.setFileName(videos.getName());
        videosDTO.setFileDownloadUri(videos.getFileuri());
        videosDTO.setSize(videos.getSize());
        videosDTO.setUsersid(videos.getUsersid() == null ? null : videos.getUsersid().getId());
        videosDTO.setTypeid(videos.getTypeid() == null ? null : videos.getTypeid().getId());
        videosDTO.setContenttype(videos.getContenttype());
        return videosDTO;
    }

    private Videos mapToEntity(UUID id, String fileName, long userid, long typeidd, final Videos videos, String fileDownloadUri, String fileType, long size) {
        videos.setId(id);
        videos.setName(fileName);
        videos.setSize(size);
        videos.setFileuri(fileDownloadUri);
        videos.setContenttype(fileType);
        if (videos.getUsersid() == null || !videos.getUsersid().getId().equals(userid)) {
            final Users videosid = usersRepository.findById(userid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "usersid not found"));
            videos.setUsersid(videosid);
        }
        if (videos.getTypeid() == null || !videos.getTypeid().getId().equals(typeidd)) {
            final VideoType typeid = videoTypeRepository.findById(typeidd)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "typeid not found"));
            videos.setTypeid(typeid);
        }

        return videos;
    }
}
