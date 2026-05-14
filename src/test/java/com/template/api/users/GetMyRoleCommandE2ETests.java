package com.template.api.users;

import com.template.api.IntegrationTests;
import com.template.api.domain.valueobject.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetMyRoleCommandE2ETests extends IntegrationTests {
    @BeforeEach
    public void beforeEach() {
        userRepository.clear();
        createFakeUser("1");
    }

    @Nested
    class HappyPath {
        @Test
        public void shouldGetMyRole() throws Exception {
            var response = mockMvc.perform(MockMvcRequestBuilders.get("/users/role")
                    .header("Authorization", createJwt()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            var result = objectMapper.readValue(response.getResponse().getContentAsString(), Role.class);

            assertNotNull(result);
            assertEquals(Role.USER, result);
        }
    }

    @Nested
    class BadPath {
        @Test
        public void shouldThrowWhenUserIsNotAuthenticate() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/users/role"))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }
    }
}
