package harbi.trust.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import harbi.trust.model.Car;

public interface CarRepo extends JpaRepository<Car,Integer>{
    @Override
    Optional<Car> findById(Integer id);
}
