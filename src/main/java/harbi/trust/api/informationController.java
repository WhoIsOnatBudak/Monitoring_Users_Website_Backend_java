package harbi.trust.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import harbi.trust.config.JwtService;
import harbi.trust.model.AppUser;
import harbi.trust.model.Car;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;
import harbi.trust.model.Role;
import harbi.trust.repo.CarRepo;
import harbi.trust.repo.UserRepo;
import java.util.Optional;

import java.util.Collections;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/info")
public class informationController {

    private final informationService Iservice;

    private final JwtService jwtService;

    private final UserRepo userRepo;

    private final CarRepo carRepo;
    
    @GetMapping("/car")
    public ResponseEntity<Car> getCar(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(carRepo.findById(101).get());
    }
    //@PreAuthorize
    @GetMapping("/everyuser")
    public ResponseEntity<List<AppUser>> getList(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix from the token
        token = token.substring(7);

        String email = jwtService.extractUsername(token);

        Optional<AppUser> find_role = userRepo.findByEmail(email);
        AppUser fond = find_role.get();

        if (fond.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(Iservice.getUsers());
         } else {
            List<Car> emptyCarList = new ArrayList<>();
            AppUser customUser = new AppUser(666, "Sorry", "You are", "Not able to see", "Others", Role.USER,emptyCarList);
            return ResponseEntity.ok(Collections.singletonList(customUser));
            
        }
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<AppUser>> getOne(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix from the token
        token = token.substring(7);

        String email = jwtService.extractUsername(token);

        Optional<AppUser> find_role = userRepo.findByEmail(email);
        AppUser fond = find_role.get();
        return ResponseEntity.ok(Collections.singletonList(fond));

    }

    @PostMapping("/changeuser")
    public ResponseEntity<AppUser> changeOne(@RequestHeader("Authorization") String token, @RequestBody ChangeRequest request) {
        // Remove "Bearer " prefix from the token
        token = token.substring(7);
    
        // Extract the username from the token
        String email = jwtService.extractUsername(token);
    
        // Find the user by email
        Optional<AppUser> find_role = userRepo.findByEmail(email);
        if (find_role.isPresent()) {
            AppUser userToUpdate = find_role.get();
    
            // Update the fields of AppUser with the new information from ChangeRequest
            userToUpdate.setFirstname(request.getFirstname());
            userToUpdate.setLastname(request.getLastname());
    
            // Save the updated user back to the database
            userRepo.save(userToUpdate);
    
            // Return the updated user
            return ResponseEntity.ok(userToUpdate);
        } else {
            // If the user is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }
    

}
