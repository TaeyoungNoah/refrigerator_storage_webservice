package springJr.foodbasket.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FoodUpdateDto {

    private String name;
    private int quantity;
    private Category category;
    private Location location;
    private LocalDateTime expirationDate;

    public FoodUpdateDto(String name, int quantity, Category category, Location location, LocalDateTime expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.location = location;
        this.expirationDate = expirationDate;
    }
}
