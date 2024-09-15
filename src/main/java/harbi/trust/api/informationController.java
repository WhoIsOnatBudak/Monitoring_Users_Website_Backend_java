package harbi.trust.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import harbi.trust.config.DataService;
import harbi.trust.config.JwtService;
import harbi.trust.model.AppUser;
import harbi.trust.model.AppUserDTO;
import harbi.trust.model.CarDTO;
import harbi.trust.repo.UserCarRepo;
import harbi.trust.model.Car;
import harbi.trust.repo.UserRepo;
import harbi.trust.model.Role;
import harbi.trust.model.UserCarId;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/info")
public class informationController {

    private final informationService informationService;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    //private final CarRepo carRepo;
    private final DataService dataService;
    private final UserCarRepo userCarRepo;


@PostMapping("/delete")
public ResponseEntity<Void> deleteRel(@RequestHeader("Authorization") String token, @RequestBody CarUserRequest request) {
    // Extract userId and carId from the request
    long userId = request.getUserId();
    long carId = request.getCarId();
    
    // Create UserCarId object using the extracted IDs
    UserCarId userCarId = new UserCarId(userId, carId);
    
    // Call the repository method to delete the record
    userCarRepo.deleteById(userCarId);

    // Return a response indicating successful deletion
    return ResponseEntity.ok().build();
}


    // Get Car by ID with owners info in DTO form
    @PostMapping("/car")
    public ResponseEntity<List<CarDTO>> getCar(@RequestHeader("Authorization") String token, @RequestBody CarRequest request) {
    

        int idForUser = request.getId();

        AppUser UserForCar = userRepo.findById(idForUser).get();
        
        List<Car> CarToReturn = UserForCar.getCars();
    
        // Convert each Car to CarDTO
        List<CarDTO> carDTOList = CarToReturn.stream()
            .map(car -> dataService.convertToCarDTO(car))
            .collect(Collectors.toList());
    
        // Return the list of CarDTOs
        return ResponseEntity.ok(carDTOList);
    }

    // Get all users (for admin only)
    @GetMapping("/everyuser")
    public ResponseEntity<List<AppUserDTO>> getList(@RequestHeader("Authorization") String token) {
        token = token.substring(7); // Remove "Bearer " prefix from the token

        String email = jwtService.extractUsername(token);
        Optional<AppUser> findRole = userRepo.findByEmail(email);

        if (findRole.isPresent()) {
            AppUser foundUser = findRole.get();
            if (foundUser.getRole() == Role.ADMIN) {
                List<AppUserDTO> usersDTO = informationService.getUsers().stream()
                        .map(dataService::convertToAppUserDTO)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(usersDTO);
            } else {
                // Return custom message for non-admins
                //List<Car> emptyCarList = new ArrayList<>();
                AppUserDTO customUser = AppUserDTO.builder()
                        .id(666)
                        .email("Sorry")
                        .firstname("You are")
                        .lastname("Not able to see")
                        .carIds(Collections.emptyList())
                        .build();
                return ResponseEntity.ok(Collections.singletonList(customUser));
            }
        }
        return ResponseEntity.status(403).build(); // Unauthorized
    }

    // Get the logged-in user's own details
    @GetMapping("/user")
    public ResponseEntity<AppUserDTO> getOne(@RequestHeader("Authorization") String token) {
        token = token.substring(7); // Remove "Bearer " prefix

        String email = jwtService.extractUsername(token);
        Optional<AppUser> findRole = userRepo.findByEmail(email);

        if (findRole.isPresent()) {
            AppUser foundUser = findRole.get();
            AppUserDTO userDTO = dataService.convertToAppUserDTO(foundUser);
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build(); // If not found
    }

    // Update logged-in user's details
    @PostMapping("/changeuser")
    public ResponseEntity<AppUserDTO> changeOne(@RequestHeader("Authorization") String token, @RequestBody ChangeRequest request) {
        token = token.substring(7); // Remove "Bearer " prefix
    
        String email = jwtService.extractUsername(token);
        Optional<AppUser> findRole = userRepo.findByEmail(email);

        if (findRole.isPresent()) {
            AppUser userToUpdate = findRole.get();

            // Update fields
            userToUpdate.setFirstname(request.getFirstname());
            userToUpdate.setLastname(request.getLastname());

            // Save updated user
            userRepo.save(userToUpdate);

            // Convert to DTO and return updated user
            AppUserDTO updatedUserDTO = dataService.convertToAppUserDTO(userToUpdate);
            return ResponseEntity.ok(updatedUserDTO);
        }
        return ResponseEntity.notFound().build(); // User not found
    }
}
