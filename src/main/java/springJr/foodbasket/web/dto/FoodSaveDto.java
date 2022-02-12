package springJr.foodbasket.web.dto;

import lombok.Getter;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
public class FoodSaveDto {

    String name;
    int quantity;
    Category category;
    Location location;
    LocalDateTime expirationDate;


    public FoodSaveDto(String name, int quantity, Category category, Location location, LocalDateTime expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.location = location;
        this.expirationDate = expirationDate;
    }
}
