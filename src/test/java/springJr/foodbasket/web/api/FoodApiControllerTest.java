package springJr.foodbasket.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import springJr.foodbasket.domain.Category;
import springJr.foodbasket.domain.Location;
import springJr.foodbasket.domain.Status;
import springJr.foodbasket.domain.foods.Food;
import springJr.foodbasket.service.FoodService;
import springJr.foodbasket.web.dto.FoodResponseDto;
import springJr.foodbasket.web.dto.FoodSaveDto;
import springJr.foodbasket.web.dto.FoodUpdateDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FoodApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FoodService foodService;

    @Autowired
    private MockMvc mvc;


    // 테스트 시점에 구애받지 않는 변수 생성
    private LocalDateTime now = LocalDateTime.now();
    private LocalDateTime nowPlusTwoDays = now.plusDays(2L);
    private LocalDateTime nowPlusOneMonth = now.plusMonths(1L);
    private LocalDateTime nowMinusTwoDays = now.minusDays(10L);


    @BeforeEach
    public void clearUp() {
        foodService.deleteAll();
    }

    // == C ==
    @Test
    public void Food_저장하기() throws Exception {
        // given
        FoodSaveDto saveDto = createSaveDto();

        String url = "http://localhost:" + port + "/foods/save";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModules(new JavaTimeModule()).writeValueAsString(saveDto)))
                .andExpect(status().isOk());

        // then
        List<FoodResponseDto> findAll = foodService.findAll();
        Assertions.assertThat(findAll.get(0).getName()).isEqualTo("test");
    }
    // == R ==
    // == U ==
    @Test
    public void Food_수정하기() throws Exception {
        // given
        FoodSaveDto saveDto = createSaveDto();
        Long saveId = foodService.saveFood(saveDto);

        FoodUpdateDto updateDto = createUpdateDto();

        String url = "http://localhost:" + port + "/foods/" + saveId + "/edit";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModules(new JavaTimeModule()).writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        // then
        FoodResponseDto findOne = foodService.findOneById(saveId);
        Assertions.assertThat(findOne.getName()).isEqualTo("update");
        Assertions.assertThat(findOne.getStatus()).isEqualTo(Status.WARNING);
    }
    // == D ==
    @Test
    public void Food_삭제하기() throws Exception {
        // given
        FoodSaveDto saveDto = createSaveDto();
        Long saveId = foodService.saveFood(saveDto);

        FoodUpdateDto updateDto = createUpdateDto();

        String url = "http://localhost:" + port + "/foods/" + saveId + "/delete";

        // when
        mvc.perform(post(url))
                .andExpect(status().isOk());

        // then
        List<FoodResponseDto> findAll = foodService.findAll();
        Assertions.assertThat(findAll.size()).isEqualTo(0);
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