package harbi.trust.config;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import harbi.trust.model.AppUser;
import harbi.trust.model.AppUserDTO;
import harbi.trust.model.CarDTO;
import harbi.trust.model.Car;

@Service
public class DataService {
    public AppUserDTO convertToAppUserDTO(AppUser user) {
    return AppUserDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstname(user.getFirstname())
        .lastname(user.getLastname())
        .carIds(user.getCars().stream()
            .map(Car::getId) // Only collect car IDs
            .collect(Collectors.toList()))
        .build();
    }
    public CarDTO convertToCarDTO(Car car){
        return CarDTO.builder()
            .id(car.getId())
            .make(car.getMake())
            .model(car.getModel())
            .year(car.getYear())
            .licensePlate(car.getLicensePlate())
            .ownerIds(car.getOwners().stream()
                .map(AppUser::getId)
                .collect(Collectors.toList()))
            .build();
    }
}
