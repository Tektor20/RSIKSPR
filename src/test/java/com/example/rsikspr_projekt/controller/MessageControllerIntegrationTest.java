package com.example.rsikspr_projekt.controller;

import com.example.rsikspr_projekt.dto.MessageRequest;
import com.example.rsikspr_projekt.model.Message;
import com.example.rsikspr_projekt.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;



@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerIntegrationTest {

    @Autowired //replacement for constructors (injecting beans)
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test //JUnit annotation to mark a test method.
    @Transactional  //All changes made in the database inside this method will be abolished (rollback)
    void sendMessage_whenValidRequest_saveMessageAndReturnCreated() throws Exception {

        MessageRequest requestDTO = new MessageRequest();
        String testContent = "This is a test message " + System.currentTimeMillis();
        requestDTO.setMessage(testContent);

        mockMvc.perform(post("v1/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.content").value(testContent))
                .andExpect(jsonPath("$.receivedAt").exists());

        List<Message> savedMessages = messageRepository.findAll();
        assertThat(savedMessages).hasSize(1);
        Message savedMessage = savedMessages.getFirst();
        assertThat(savedMessage.getContent()).isEqualTo(testContent);
        assertThat(savedMessage.getReceivedAt()).isNotNull();
        assertThat(savedMessage.getId()).isNotNull();

    }

    @Test
    @Transactional
    void sendMessage_whenMessageIsBlank_returnBadRequest() throws Exception {
        MessageRequest requestDTO = new MessageRequest();
        requestDTO.setMessage("");

        mockMvc.perform(post("v1/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());

        List<Message> savedMessages = messageRepository.findAll();
        assertThat(savedMessages).isEmpty();
    }

    @Test
    @Transactional
    void sendMessage_whenMessageIsNull_returnBadRequest() throws Exception {
        MessageRequest requestDTO = new MessageRequest();
        requestDTO.setMessage(null);

        mockMvc.perform(post("v1/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());

        List<Message> savedMessages = messageRepository.findAll();
        assertThat(savedMessages).isEmpty();
    }

}
