package springJr.foodbasket.web.dto;

import lombok.Getter;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;
import springJr.foodbasket.domain.foods.Food;

import java.time.LocalDateTime;

@Getter
public class FoodResponseDto {

    String name;
    int quantity;
    Category category;
    Location location;
    Status status;
    LocalDateTime expirationDate;


    public FoodResponseDto(Food entity) {
        this.name = entity.getName();
        this.quantity = entity.getQuantity();
        this.category = entity.getCategory();
        this.location = entity.getLocation();
        this.status = entity.getStatus();
        this.expirationDate = entity.getExpirationDate();
    }
}
