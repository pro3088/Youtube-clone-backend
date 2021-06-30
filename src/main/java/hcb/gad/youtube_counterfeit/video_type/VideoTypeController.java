package hcb.gad.youtube_counterfeit.video_type;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/videoTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoTypeController {

    private final VideoTypeService videoTypeService;

    public VideoTypeController(final VideoTypeService videoTypeService) {
        this.videoTypeService = videoTypeService;
    }

    @GetMapping
    public ResponseEntity<List<VideoTypeDTO>> getAllVideoTypes() {
        return ResponseEntity.ok(videoTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoTypeDTO> getVideoType(@PathVariable final Long id) {
        return ResponseEntity.ok(videoTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createVideoType(
            @RequestBody @Valid final VideoTypeDTO videoTypeDTO) {
        return new ResponseEntity<>(videoTypeService.create(videoTypeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVideoType(@PathVariable final Long id,
            @RequestBody @Valid final VideoTypeDTO videoTypeDTO) {
        videoTypeService.update(id, videoTypeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoType(@PathVariable final Long id) {
        videoTypeService.deletebyid(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteall() {
        videoTypeService.deleteall();
        return ResponseEntity.noContent().build();
    }

}
