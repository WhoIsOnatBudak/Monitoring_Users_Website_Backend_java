package harbi.trust.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserCarId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "car_id")
    private Long carId;

    // Default constructor
    public UserCarId() {
    }

    // Parameterized constructor
    public UserCarId(Long userId, Long carId) {
        this.userId = userId;
        this.carId = carId;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    // equals() and hashCode() methods to compare composite keys correctly
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCarId that = (UserCarId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(carId, that.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, carId);
    }
}
