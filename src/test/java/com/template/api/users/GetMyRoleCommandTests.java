package com.template.api.users;

import com.template.api.UnitTests;
import com.template.api.application.usecases.user.GetMyRoleCommand;
import com.template.api.application.usecases.user.GetMyRoleCommandHandler;
import com.template.api.domain.valueobject.Role;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetMyRoleCommandTests extends UnitTests {
    @Nested
    class HappyPath {
        @Test
        public void shouldGetRole() {
            var command = new GetMyRoleCommand(Role.USER);
            var handler = new GetMyRoleCommandHandler();
            var result = handler.handle(command);
            assertNotNull(result);
            assertEquals(Role.USER, result);
        }
    }
}
