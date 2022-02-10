package springJr.foodbasket.domain.foods;

import springJr.foodbasket.domain.BaseTimeEntity;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Food extends BaseTimeEntity {

    @Id
    @GeneratedValue
    Long id;

    @Column(length = 20, nullable = false)
    String name;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    Category category;

    @Column(nullable = false)
    Location location;

    @Column(nullable = false)
    Status status;

    @Column(nullable = true)
    LocalDateTime expirationDate;
}
