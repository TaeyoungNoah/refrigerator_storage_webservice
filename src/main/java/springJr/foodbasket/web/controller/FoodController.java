package springJr.foodbasket.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springJr.foodbasket.web.dto.FoodSaveDto;

@Controller
@RequestMapping("/foods")
public class FoodController {

    // == C ==
    @GetMapping("/save")
    public String createFoodForm() {
        return "foods/createFoodForm";
    }

    @PostMapping("/save")
    public String saveFood(FoodSaveDto foodSaveDto) {
        return "redirect:/foods";
    }

    // == R ==
    @GetMapping("/")
    public String list(Model model) {
        return "foods/foodList";
    }

    @GetMapping("/{foodId}")
    public String details() {
        return "foods/foodForm";
    }

    // == U ==
    @GetMapping("/{foodId}/edit")
    public String updateFoodForm() {
        return "foods/updateFoodForm";
    }

    @PostMapping("/{foodId}/edit")
    public String editFood() {
        return "redirect://{itemId}";
    }

    // == D ==
    @PostMapping("/{foodId}/delete")
    public String deleteFood() {
        return "redirect:/foods";
    }
}
