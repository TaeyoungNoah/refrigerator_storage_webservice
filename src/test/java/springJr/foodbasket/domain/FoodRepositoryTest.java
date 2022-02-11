package springJr.foodbasket.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import springJr.foodbasket.domain.foods.Food;
import springJr.foodbasket.domain.foods.FoodRepository;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class FoodRepositoryTest {

    @Autowired
    FoodRepository foodRepository;

    @AfterEach
    public void cleanUp() {
        foodRepository.deleteAll();
    }

    @Test
    public void Entity_저장_조회() throws Exception {
        // given
        Food food = Food.builder()
                .name("test")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(LocalDateTime.of(2022,3,3,3,3))
                .build();

        // when
        Long saveId = foodRepository.save(food);

        // then
        Food findOne = foodRepository.findOneById(saveId);

        Assertions.assertThat(findOne.getName()).isEqualTo("test");
        Assertions.assertThat(findOne.getQuantity()).isEqualTo(1);
        Assertions.assertThat(findOne.getLocation()).isEqualTo(Location.FREEZER);
    }

//    @Test
//    public void 전체조회() throws Exception {
//        // given
//        Food food1 = Food.builder()
//                .name("test1")
//                .quantity(1)
//                .category(Category.FRUIT)
//                .location(Location.FREEZER)
//                .expirationDate(LocalDateTime.of(2022,3,3,3,3))
//                .build();
//
//        Food food2 = Food.builder()
//                .name("test2")
//                .quantity(2)
//                .category(Category.FRUIT)
//                .location(Location.FREEZER)
//                .expirationDate(LocalDateTime.of(2022,3,3,3,3))
//                .build();
//
//
//        Long saveId1 = foodRepository.save(food1);
//        Long saveId2 = foodRepository.save(food2);
//
//
//        // when
//        List<Food> foods = foodRepository.findAll();
//
//        // then
//        Assertions.assertThat(foods.size()).isEqualTo(2);
//        Assertions.assertThat(foods.get(0).getName()).isEqualTo("test1");
//        Assertions.assertThat(foods.get(1).getName()).isEqualTo("test2");
//    }

    @Test
    public void BaseTimeEntity_등록확인() throws Exception {
        // given
        LocalDateTime flag = LocalDateTime.of(2021,1,1,1,1);
        Food food = Food.builder()
                .name("test")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(LocalDateTime.of(2022,3,3,3,3))
                .build();
        // when
        Long saveId = foodRepository.save(food);

        // then
        Food findOne = foodRepository.findOneById(saveId);

        System.out.println(findOne.getInitDate());

        Assertions.assertThat(findOne.getInitDate()).isAfter(flag);
    }

    @Test
    public void Food의_Status생성확인_SAFE() throws Exception {
        // given
        LocalDateTime flag = LocalDateTime.of(2022,2, 19,0,0);
        Food food = Food.builder()
                .name("test")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(flag)
                .build();
        // when
        Long saveId = foodRepository.save(food);

        // then
        Food findOne = foodRepository.findOneById(saveId);
        Assertions.assertThat(findOne.getStatus()).isEqualTo(Status.SAFE);
    }
    
    @Test
    public void Food의_Status생성확인_WARNING() throws Exception {
        // given
        LocalDateTime flag = LocalDateTime.of(2022,2, 13,0,0);
        Food food = Food.builder()
                .name("test")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(flag)
                .build();
        // when
        Long saveId = foodRepository.save(food);

        // then
        Food findOne = foodRepository.findOneById(saveId);
        Assertions.assertThat(findOne.getStatus()).isEqualTo(Status.WARNING);
    }

    @Test
    public void Food의_Status생성확인_DANGER() throws Exception {
        // given
        LocalDateTime flag = LocalDateTime.of(2022,2, 10,0,0);
        Food food = Food.builder()
                .name("test")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(flag)
                .build();
        // when
        Long saveId = foodRepository.save(food);

        // then
        Food findOne = foodRepository.findOneById(saveId);
        Assertions.assertThat(findOne.getStatus()).isEqualTo(Status.DANGER);
    }

    @Test
    public void Food의_Status생성확인_NONE() throws Exception {
        // given

        Food food = Food.builder()
                .name("test")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(null)
                .build();
        // when
        Long saveId = foodRepository.save(food);

        // then
        Food findOne = foodRepository.findOneById(saveId);
        Assertions.assertThat(findOne.getStatus()).isEqualTo(Status.NONE);
    }
}
