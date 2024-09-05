package harbi.trust.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import harbi.trust.model.AppUser;
import java.util.Optional;


public interface UserRepo extends JpaRepository<AppUser,Integer>{
    Optional<AppUser> findByEmail(String email);
}
