package hcb.gad.youtube_counterfeit;

import hcb.gad.youtube_counterfeit.videos.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class YoutubeCounterfeitApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeCounterfeitApplication.class, args);
    }

}
