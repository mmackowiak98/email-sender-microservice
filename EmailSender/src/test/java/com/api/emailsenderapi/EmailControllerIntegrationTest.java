package com.api.emailsenderapi;

import com.api.emailsenderapi.controller.EmailController;
import com.api.emailsenderapi.exceptions.EmailNotFoundException;
import com.api.emailsenderapi.model.Email;
import com.api.emailsenderapi.model.dto.EmailDTO;
import com.api.emailsenderapi.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmailController.class)
public class EmailControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveEmail() throws Exception {
        Email email = new Email();
        email.setEmailAddress("test@example.com");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/email/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        verify(emailService, times(1)).saveEmail(any(Email.class));
        assertNotNull(responseContent);
    }
    @Test
    void testFindEmailByIdWithNonexistentId() throws Exception {
        when(emailService.getEmailById(anyLong())).thenThrow(new EmailNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/email/getById")
                        .param("id", "999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(emailService, times(1)).getEmailById(anyLong());
    }

    @Test
    void testUpdateEmail() throws Exception {
        Email updatedEmail = new Email();
        updatedEmail.setEmailAddress("updated@example.com");
        when(emailService.updateEmail(anyLong(), any(String.class))).thenReturn(new EmailDTO());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/email/update")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmail)))
                .andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();

        verify(emailService, times(1)).updateEmail(anyLong(), any(String.class));
        assertNotNull(result);
    }

    @Test
    void testDeleteEmail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/email/delete")
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(emailService, times(1)).deleteById(anyLong());
    }


    @Test
    void testFindEmailByAddress() throws Exception {
        Email email = new Email();
        email.setEmailAddress("example@gmail.com");
        when(emailService.getEmailByAddress(anyString())).thenReturn(new EmailDTO());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/email/get")
                        .param("email", email.getEmailAddress()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        EmailDTO responseEmail = objectMapper.readValue(responseContent, EmailDTO.class);

        verify(emailService, times(1)).getEmailByAddress(anyString());
        assertNotNull(responseEmail);
    }
}