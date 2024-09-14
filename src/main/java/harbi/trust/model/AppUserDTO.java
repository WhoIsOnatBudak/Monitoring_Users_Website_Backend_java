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
public class AppUserDTO {
    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    private List<Integer> carIds; // Only the IDs of the cars
}