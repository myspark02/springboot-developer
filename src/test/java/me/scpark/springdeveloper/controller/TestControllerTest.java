package me.scpark.springdeveloper.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /test 요청 시 Hello World 반환")
    void getTestAPI()  throws Exception {
        // Given : (없음)

        // When & Then
        mockMvc.perform(get("/test")).andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());

    }
}
