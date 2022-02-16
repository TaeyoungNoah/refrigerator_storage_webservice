package springJr.foodbasket.domain.foods;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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


    public List<Food> findByLocation(Location location) {
        List<Food> findByLocation = em.createQuery("select r from Food r where r.location = :location", Food.class)
                .setParameter("location", location)
                .getResultList();
        for (Food food : findByLocation) {
            food.checkStatusLater();
        }
        return findByLocation;
    }

//    public List<Food> findByStatus(Status status) {
//        List<Food> findByLocation = em.createQuery("select s from Food s where s.status = :status", Food.class)
//                .setParameter("status", status)
//                .getResultList();
//    } 이렇게 하면 조회 하고 status 를 업데이트 해버려서 의미가 없어져버림 이거는 서비스단에서 필터링 하는게 맞을지 고민중



    // D
    public void deleteById(Long id) {
        em.remove(findOneById(id));
    }

    public void deleteAll() {
        em.createQuery("delete from Food f");
    }

}
