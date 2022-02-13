package springJr.foodbasket.web.dto;

import lombok.Getter;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;
import springJr.foodbasket.domain.foods.Food;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
public class FoodSaveDto {

    private String name;
    private int quantity;
    private Category category;
    private Location location;
    private LocalDateTime expirationDate;


    public FoodSaveDto(String name, int quantity, Category category, Location location, LocalDateTime expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.location = location;
        this.expirationDate = expirationDate;
    }

    public Food toEntity() {
        return Food.builder()
                .name(this.name)
                .quantity(this.quantity)
                .category(this.category)
                .location(this.getLocation())
                .expirationDate(this.getExpirationDate())
                .build();
    }
}
