package harbi.trust.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTO {
    private Integer id;
    private String make;
    private String model;
    private String year;
    private String licensePlate;
    private List<Integer> ownerIds; // Only the IDs of the owners
    private String type;

    // Subtype-specific fields
    private Integer horsepower; // For SuperCar
    private Integer price;   // For CheapCar
}