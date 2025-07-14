package yun.edu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/elm_servlet_war_exploded/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"666\", \"password\": \"666\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(post("/elm_servlet_war_exploded/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"wrongUser\", \"password\": \"wrongPassword\" }"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("用户名或密码错误"));
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        mockMvc.perform(post("/elm_servlet_war_exploded/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"333\", \"password\": \"333\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("注册成功"));
    }

    @Test
    public void testRegisterFailure() throws Exception {
        mockMvc.perform(post("/elm_servlet_war_exploded/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"existingUser\", \"password\": \"existingPassword\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("注册失败，用户名已存在"));
    }
}