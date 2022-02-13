package springJr.foodbasket.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;
import springJr.foodbasket.domain.foods.Food;
import springJr.foodbasket.domain.foods.FoodRepository;
import springJr.foodbasket.web.dto.FoodResponseDto;
import springJr.foodbasket.web.dto.FoodSaveDto;
import springJr.foodbasket.web.dto.FoodUpdateDto;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@Transactional
public class FoodServiceTest {

    @Autowired
    FoodService foodService;

    @Autowired
    FoodRepository foodRepository;

    // 테스트 시점에 구애받지 않는 변수 생성
    private LocalDateTime now = LocalDateTime.now();
    private LocalDateTime nowPlusTwoDays = now.plusDays(2L);
    private LocalDateTime nowPlusOneMonth = now.plusMonths(1L);
    private LocalDateTime nowMinusTwoDays = now.minusDays(10L);


    // == C ==
    @Test
//    @Rollback(value = false)
    public void Food_저장() throws Exception {
        // given
        FoodSaveDto saveDto = createSaveDto();

        // when
        Long saveId = foodService.saveFood(saveDto);

        // then
        List<Food> foods = foodRepository.findAll();
        Assertions.assertThat(foods.get(0).getName()).isEqualTo("test");

    }


    // == R ==
    @Test
    public void Food_조회_하나() throws Exception {
        // given
        FoodSaveDto saveDto = createSaveDto();

        // when
        Long saveId = foodService.saveFood(saveDto);

        // then
        FoodResponseDto saveOne = foodService.findOneById(saveId);
        Assertions.assertThat(saveOne.getName()).isEqualTo("test");
        Assertions.assertThat(saveOne.getQuantity()).isEqualTo(3);
    }

    @Test
//    @Rollback(value = false)
    public void Food_조회_전체() throws Exception {
        // given
        FoodSaveDto saveDto1 = createSaveDto();
        FoodSaveDto saveDto2 = new FoodSaveDto(
                "test2",
                3,
                Category.FRUIT,
                Location.FREEZER,
                LocalDateTime.of(2022, 3, 3, 0, 0)
        );

        foodService.saveFood(saveDto1);
        foodService.saveFood(saveDto2);

        // when
        List<FoodResponseDto> findAll = foodService.findAll();

        // then
        Assertions.assertThat(findAll.size()).isEqualTo(2);
    }

    // == U ==
    @Test
    public void Food_수정하기() throws Exception {
        // given
        FoodSaveDto saveDto = createSaveDto();
        Long saveId = foodService.saveFood(saveDto);

        // when
        FoodUpdateDto updateDto = createUpdateDto();
        Long updateId = foodService.update(saveId, updateDto);
        // then

        Food findOne = foodRepository.findOneById(updateId);

        Assertions.assertThat(saveId).isEqualTo(updateId);
        Assertions.assertThat(findOne.getName()).isEqualTo("update");
        Assertions.assertThat(findOne.getQuantity()).isEqualTo(2);
        Assertions.assertThat(findOne.getCategory()).isEqualTo(Category.MEAT);
        Assertions.assertThat(findOne.getLocation()).isEqualTo(Location.REFRIGERATOR);
        Assertions.assertThat(findOne.getExpirationDate()).isEqualTo(nowPlusTwoDays);
    }

    @Test
    public void Food_수정후_상태변화() throws Exception {
        // given
        Long saveId = foodService.saveFood(createSaveDto());
        FoodResponseDto beforeUpdate = foodService.findOneById(saveId);

        Assertions.assertThat(beforeUpdate.getStatus()).isEqualTo(Status.SAFE);

        // when
        Long updateId = foodService.update(saveId, createUpdateDto());

        // then
        FoodResponseDto afterUpdate = foodService.findOneById(updateId);
        Assertions.assertThat(afterUpdate.getStatus()).isEqualTo(Status.WARNING);
    }

    // == D ==
    @Test
    public void Food_삭제() throws Exception {
        // given
        FoodSaveDto saveDto = createSaveDto();

        // when

        Long saveId = foodService.saveFood(saveDto);
        List<FoodResponseDto> findAll = foodService.findAll();
        Assertions.assertThat(findAll.size()).isEqualTo(1);

        foodService.deleteOneById(saveId);

        // then
        List<FoodResponseDto> findAll2 = foodService.findAll();
        Assertions.assertThat(findAll2.size()).isEqualTo(0);
    }


    private FoodSaveDto createSaveDto() {
        return new FoodSaveDto(
                "test",
                3,
                Category.FRUIT,
                Location.FREEZER,
                nowPlusOneMonth
        );
    }

    private FoodUpdateDto createUpdateDto() {
        return new FoodUpdateDto(
                "update",
                2,
                Category.MEAT,
                Location.REFRIGERATOR,
                nowPlusTwoDays
        );
    }
}
