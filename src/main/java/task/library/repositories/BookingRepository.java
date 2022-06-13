package task.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.library.entities.Booking;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
