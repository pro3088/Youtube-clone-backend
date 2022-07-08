package hcb.rotimi.Youtube_Clone.video_type;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VideoTypeDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String types;

}
