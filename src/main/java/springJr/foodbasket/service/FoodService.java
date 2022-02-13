package springJr.foodbasket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springJr.foodbasket.domain.foods.Food;
import springJr.foodbasket.domain.foods.FoodRepository;
import springJr.foodbasket.web.dto.FoodResponseDto;
import springJr.foodbasket.web.dto.FoodSaveDto;
import springJr.foodbasket.web.dto.FoodUpdateDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;


    // C
    public Long saveFood(FoodSaveDto saveDto) {
        Food food = saveDto.toEntity();
        return foodRepository.save(food);
    }

    // R
    public FoodResponseDto findOneById(Long id) {
        Food findOne = foodRepository.findOneById(id);
        return new FoodResponseDto(findOne);
    }

    public List<FoodResponseDto> findAll() {
        List<Food> findAll = foodRepository.findAll();
        List<FoodResponseDto> listDto = new ArrayList<>();
        for (Food food : findAll) {
            listDto.add(new FoodResponseDto(food));
        }
        return listDto;
    }

    // U
    public Long update(Long foodId, FoodUpdateDto foodUpdateDto) {
        Food findOne = foodRepository.findOneById(foodId);
        findOne.update(foodUpdateDto.getName(),
                foodUpdateDto.getQuantity(),
                foodUpdateDto.getCategory(),
                foodUpdateDto.getLocation(),
                foodUpdateDto.getExpirationDate());
        return foodId;
    }

    // D
    public void deleteOneById(Long id) {
        foodRepository.deleteById(id);
    }

    public void deleteAll() {
        foodRepository.deleteAll();
    }
}
