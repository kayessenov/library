package task.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.library.entities.Genre;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
