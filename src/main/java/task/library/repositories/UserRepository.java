package task.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.library.entities.Users;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

}