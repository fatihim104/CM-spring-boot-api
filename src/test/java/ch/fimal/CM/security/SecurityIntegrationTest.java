package ch.fimal.CM.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.fimal.CM.dto.RoleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldBlockUnauthenticatedAccessToAdminEndpoints() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/admin/role")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new RoleRequest("ROLE_TEST"))))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldBlockNonAdminAccessToAdminEndpoints() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/admin/role")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new RoleRequest("ROLE_TEST"))))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void shouldAllowAdminAccessToAdminEndpoints() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/admin/role")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new RoleRequest("ROLE_TEST_ADMIN"))))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @WithMockUser(authorities = "course:create")
  void shouldAllowCourseCreateWithAuthority() throws Exception {
    String courseJson = """
            {
                "name": "Test Course",
                "place": "Online",
                "startDate": "2099-01-01",
                "status": "PLANNING"
            }
        """;

    mockMvc.perform(MockMvcRequestBuilders.post("/courses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(courseJson))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldBlockCourseCreateWithoutAuthority() throws Exception {
    String courseJson = """
            {
                "name": "Test Course",
                "place": "Online",
                "startDate": "2099-01-01",
                "status": "PLANNING"
            }
        """;

    mockMvc.perform(MockMvcRequestBuilders.post("/courses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(courseJson))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }
}
