package hcb.gad.youtube_counterfeit.video_type;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class VideoTypeService {

    private final VideoTypeRepository videoTypeRepository;

    public VideoTypeService(final VideoTypeRepository videoTypeRepository) {
        this.videoTypeRepository = videoTypeRepository;
    }

    public List<VideoTypeDTO> findAll() {
        return videoTypeRepository.findAll()
                .stream()
                .map(videoType -> mapToDTO(videoType, new VideoTypeDTO()))
                .collect(Collectors.toList());
    }

    public VideoTypeDTO get(final Long id) {
        return videoTypeRepository.findById(id)
                .map(videoType -> mapToDTO(videoType, new VideoTypeDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final VideoTypeDTO videoTypeDTO) {
        final VideoType videoType = new VideoType();
        mapToEntity(videoTypeDTO, videoType);
        return videoTypeRepository.save(videoType).getId();
    }

    public void update(final Long id, final VideoTypeDTO videoTypeDTO) {
        final VideoType videoType = videoTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(videoTypeDTO, videoType);
        videoTypeRepository.save(videoType);
    }

    public void deletebyid(final Long id) {
        videoTypeRepository.deleteById(id);
    }

    public void deleteall() {
        videoTypeRepository.deleteAll();
    }

    private VideoTypeDTO mapToDTO(final VideoType videoType, final VideoTypeDTO videoTypeDTO) {
        videoTypeDTO.setId(videoType.getId());
        videoTypeDTO.setTypes(videoType.getTypes());
        return videoTypeDTO;
    }

    private VideoType mapToEntity(final VideoTypeDTO videoTypeDTO, final VideoType videoType) {
        videoType.setTypes(videoTypeDTO.getTypes());
        return videoType;
    }

}
