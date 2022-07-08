package hcb.rotimi.Youtube_Clone.users;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UsersDTO> findAll() {
        return usersRepository.findAll()
                .stream()
                .map(users -> mapToDTO(users, new UsersDTO()))
                .collect(Collectors.toList());
    }

    public UsersDTO get(final Long id) {
        return usersRepository.findById(id)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        return usersRepository.save(users).getId();
    }

    public void update(final Long id, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void deletebyid(final Long id) {
        usersRepository.deleteById(id);
    }

    public void deleteall() {
        usersRepository.deleteAll();
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setId(users.getId());
        usersDTO.setName(users.getName());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setGender(users.getGender());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setName(usersDTO.getName());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        users.setUsername(usersDTO.getUsername());
        users.setGender(usersDTO.getGender());
        return users;
    }

}
