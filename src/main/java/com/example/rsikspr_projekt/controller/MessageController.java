package com.example.rsikspr_projekt.controller;

import com.example.rsikspr_projekt.dto.MessageRequest;
import com.example.rsikspr_projekt.model.Message;
import com.example.rsikspr_projekt.repository.MessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/v1")
@Tag(name = "Message API", description = "API for sending and receiving messages")
public class MessageController {

    private final static Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @PostMapping("/send")
    @Operation(summary = "receive and save message",
            description = "receives JSON object with the message," +
                    " saves it in a database along with the time it was received " +
                    "and returns the saved message",
            responses =  {
                @ApiResponse(responseCode = "201", description = "message received",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            })
    public ResponseEntity<Message> sendMessage(@Valid
                                                @RequestBody
                                                MessageRequest messageRequest) {
        log.info(messageRequest.toString());

        try{
            Message newMessage = new Message();
            newMessage.setContent(messageRequest.getMessage());
            newMessage.setReceivedAt(LocalDateTime.now());

            Message savedMessage = messageRepository.save(newMessage);
            log.info("Message saved with ID {}", savedMessage.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
        } catch (Exception e){
            log.error("Error saving message: {}", messageRequest.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
