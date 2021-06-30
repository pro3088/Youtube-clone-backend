package hcb.gad.youtube_counterfeit.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private Integer password;

    @NotNull
    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String gender;

}
