package hcb.rotimi.Youtube_Clone;

import hcb.rotimi.Youtube_Clone.videos.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Youtube_Clone {

    public static void main(String[] args) {
        SpringApplication.run(Youtube_Clone.class, args);
    }

}
