package harbi.trust.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import harbi.trust.model.UserCar;
import harbi.trust.model.UserCarId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserCarRepo extends JpaRepository<UserCar, UserCarId> {

    // Native SQL query using actual column names
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_cars WHERE user_id = :userId AND car_id = :carId", nativeQuery = true)
    void deleteByUserIdAndCarId(Long userId, Long carId);
}
