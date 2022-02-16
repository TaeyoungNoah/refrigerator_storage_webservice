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

    // 테스트 시점에 구애받지 않는 변수 생성
    private LocalDateTime now = LocalDateTime.now();
    private LocalDateTime nowPlusTwoDays = now.plusDays(2L);
    private LocalDateTime nowPlusOneMonth = now.plusMonths(1L);
    private LocalDateTime nowMinusTwoDays = now.minusDays(10L);

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
                .expirationDate(nowPlusOneMonth)
                .build();

        // when
        Long saveId = foodRepository.save(food);

        // then
        Food findOne = foodRepository.findOneById(saveId);

        Assertions.assertThat(findOne.getName()).isEqualTo("test");
        Assertions.assertThat(findOne.getQuantity()).isEqualTo(1);
        Assertions.assertThat(findOne.getLocation()).isEqualTo(Location.FREEZER);
    }

    @Test
    public void 전체조회() throws Exception {
        // given
        Food food1 = Food.builder()
                .name("test1")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(nowPlusOneMonth)
                .build();

        Food food2 = Food.builder()
                .name("test2")
                .quantity(2)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(nowPlusOneMonth)
                .build();


        Long saveId1 = foodRepository.save(food1);
        Long saveId2 = foodRepository.save(food2);


        // when
        List<Food> foods = foodRepository.findAll();

        // then
        Assertions.assertThat(foods.size()).isEqualTo(2);
        Assertions.assertThat(foods.get(0).getName()).isEqualTo("test1");
        Assertions.assertThat(foods.get(1).getName()).isEqualTo("test2");
    }

    @Test
    public void 냉장실_조회() throws Exception {
        // given
        Food foodRef = Food.builder()
                .name("REFRIGERATOR")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.REFRIGERATOR)
                .expirationDate(nowPlusOneMonth)
                .build();
        Food foodFre = Food.builder()
                .name("FREEZER")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(nowPlusOneMonth)
                .build();

        foodRepository.save(foodRef);
        foodRepository.save(foodFre);

        // when
        List<Food> allRefrigerator = foodRepository.findByLocation(Location.REFRIGERATOR);

        // then
        Assertions.assertThat(allRefrigerator.get(0).getName()).isEqualTo("REFRIGERATOR");
        Assertions.assertThat(allRefrigerator.get(0).getLocation()).isEqualTo(Location.REFRIGERATOR);
        Assertions.assertThat(allRefrigerator.size()).isEqualTo(1);


    }

    @Test
    public void 냉동실_조회() throws Exception {
        // given
        Food foodRef = Food.builder()
                .name("REFRIGERATOR")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.REFRIGERATOR)
                .expirationDate(nowPlusOneMonth)
                .build();
        Food foodFre = Food.builder()
                .name("FREEZER")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(nowPlusOneMonth)
                .build();

        foodRepository.save(foodRef);
        foodRepository.save(foodFre);

        // when
        List<Food> allFreezer = foodRepository.findByLocation(Location.FREEZER);

        // then
        Assertions.assertThat(allFreezer.get(0).getName()).isEqualTo("FREEZER");
        Assertions.assertThat(allFreezer.get(0).getLocation()).isEqualTo(Location.FREEZER);
        Assertions.assertThat(allFreezer.size()).isEqualTo(1);


    }

    @Test
    public void BaseTimeEntity_등록확인() throws Exception {
        // given
        LocalDateTime flag = LocalDateTime.of(2021,1,1,1,1);
        Food food = Food.builder()
                .name("test")
                .quantity(1)
                .category(Category.FRUIT)
                .location(Location.FREEZER)
                .expirationDate(nowPlusOneMonth)
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
        LocalDateTime flag = nowPlusOneMonth;
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
        LocalDateTime flag = nowPlusTwoDays;
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
        LocalDateTime flag = nowMinusTwoDays;
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
