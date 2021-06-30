package hcb.gad.youtube_counterfeit.videos.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UploadFileDTO {


    private UUID id;
    private String fileName;
    private String fileDownloadUri;
    private long size;

    private String contenttype;

    private Long typeid;
    private Long usersid;

    public UploadFileDTO(){

    }

    public UploadFileDTO(UUID id,String fileName, String fileDownloadUri, String contenttype, long size, long user, long type) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.size = size;
        this.usersid =user;
        this.typeid = type;
        this.id = id;
        this.contenttype = contenttype;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


}
