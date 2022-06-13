package task.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.library.entities.Books;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
}
