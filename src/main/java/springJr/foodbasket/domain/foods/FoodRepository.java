package springJr.foodbasket.domain.foods;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FoodRepository {

    private final EntityManager em;

    // C
    public Long save(Food food) {
        em.persist(food);
        food.checkStatusInit();
        return food.getId();
    }

    // R
    public Food findOneById(Long id) {
        Food food = em.find(Food.class, id);
        food.checkStatusLater();
        return food;
    }

    public List<Food> findAll() {
        List<Food> findAll = em.createQuery("select f from Food f", Food.class)
                .getResultList();
        for (Food food : findAll) {
            food.checkStatusLater();
        }
        return findAll;
    }

    // D
    public void deleteById(Long id) {
        em.remove(findOneById(id));
    }

    public void deleteAll() {
        em.createQuery("delete from Food f");
    }

}
