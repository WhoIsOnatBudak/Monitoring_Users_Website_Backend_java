package harbi.trust.api;

import java.util.List;

import org.springframework.stereotype.Service;

import harbi.trust.model.AppUser;
import harbi.trust.repo.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class informationService {

    private final UserRepo userRepo;

    
    public List<AppUser> getUsers(){

        return userRepo.findAll();
    }
}
