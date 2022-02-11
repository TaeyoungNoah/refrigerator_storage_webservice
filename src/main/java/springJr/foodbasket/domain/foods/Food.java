package springJr.foodbasket.domain.foods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springJr.foodbasket.domain.BaseTimeEntity;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Getter
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

    @Builder
    public Food(String name, int quantity, Category category, Location location, LocalDateTime expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.location = location;
        this.expirationDate = expirationDate;
    }


    // 상태판별
    public void checkStatus() {
        final long FLAG_DATE = 2L;
        if (expirationDate == null) {
            this.status = Status.NONE;
            return;
        }
        long between = ChronoUnit.DAYS.between(getInitDate(), expirationDate);
        if (between <= 0) {
            this.status = Status.DANGER;
            return;
        }
        if (between > FLAG_DATE) {
            this.status = Status.SAFE;
            return;
        }
        this.status = Status.WARNING;
        return;
    }


    // Update
    public void update(String name, int quantity, Category category, Location location, LocalDateTime expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.location = location;
        this.expirationDate = expirationDate;
    }
}
