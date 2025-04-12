package com.example.rsikspr_projekt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Payload for sending new messages")
public class MessageRequest {

    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 500, message = "Message content cannot exceed 500 characters")
    @Getter @Setter
    private String message;
}
