package springJr.foodbasket.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springJr.foodbasket.service.FoodService;
import springJr.foodbasket.web.dto.FoodResponseDto;
import springJr.foodbasket.web.dto.FoodSaveDto;
import springJr.foodbasket.web.dto.FoodUpdateDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FoodApiController {

    private final FoodService foodService;

    // == C ==
    @PostMapping("/foods/save")
    public Long saveFood(@RequestBody FoodSaveDto foodSaveDto) {
        return foodService.saveFood(foodSaveDto);
    }

    // == R ==
    // 저장한 전체 음식 조회
    @GetMapping("/foods")
    public List<FoodResponseDto> foodsList() {
        return foodService.findAll();
    }

    // 하나의 음식 상세조회
    @GetMapping("/foods/{foodId}")
    public FoodResponseDto findFoodById(@PathVariable Long foodId) {
        return foodService.findOneById(foodId);
    }


    // == U ==
    @PostMapping("/foods/{foodId}/edit")
    public Long updateFood(@PathVariable Long foodId,
                           @RequestBody FoodUpdateDto foodUpdateDto) {
        return foodService.update(foodId, foodUpdateDto);
    }

    // == D ==
    @PostMapping("/foods/{foodId}/delete")
    public void deleteFood(@PathVariable Long foodId) {
        foodService.deleteOneById(foodId);
    }
}
