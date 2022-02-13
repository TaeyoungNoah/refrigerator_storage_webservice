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
import springJr.foodbasket.domain.foods.Food;
import springJr.foodbasket.domain.foods.FoodRepository;
import springJr.foodbasket.web.dto.FoodResponseDto;
import springJr.foodbasket.web.dto.FoodSaveDto;

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
    @Rollback(value = false)
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
        Assertions.assertThat(findAll.size()).isEqualTo(1);
    }


    private FoodSaveDto createSaveDto() {
        return new FoodSaveDto(
                "test",
                3,
                Category.FRUIT,
                Location.FREEZER,
                LocalDateTime.of(2022,3,3,0,0)
        );
    }
}
