package task.library.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import task.library.entities.Users;

public interface UserService extends UserDetailsService {

    Users registerUser(Users user);

    Users updateAvatar(Users user);
    boolean updatePassword(Users user, String oldPassword, String newPassword);

}
