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
        food.checkStatus();
        return food.getId();
    }

    // R
    public Food findOneById(Long id) {
        return em.find(Food.class, id);
    }

    public List<Food> findAll() {
        return em.createQuery("select f from Food f", Food.class)
                .getResultList();
    }

    // D
    public void deleteById(Long id) {
        em.remove(findOneById(id));
    }

    public void deleteAll() {
        em.createQuery("delete from Food f");
    }

}
