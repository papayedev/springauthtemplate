package com.template.api.auth;

import com.template.api.IntegrationTests;
import com.template.api.auth.domain.viewmodel.AccessTokenViewModel;
import com.template.api.auth.presentation.dto.RefreshTokenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RefreshTokenE2ETests extends IntegrationTests {
    @BeforeEach
    public void setUp() {
        userRepository.clear();
        createFakeUser();
        activateUser();
    }

    @Nested
    class HappyPath {
        @Test
        public void shouldRefreshToken() throws Exception {
            var dto = new RefreshTokenDTO(createAccessAndRefreshToken().getRefreshToken());

            var response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsBytes(dto)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var accessToken = objectMapper.readValue(response.getResponse().getContentAsString(), AccessTokenViewModel.class);

            assertNotNull(accessToken);
            assertNotNull(accessToken.getAccessToken());
        }
    }

    @Nested
    class BadPath {
        @Test
        void shouldNotRefreshTokenIfTokenIsBad() throws Exception {
            var dto = new RefreshTokenDTO("");

            mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsBytes(dto)))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }
    }
}
