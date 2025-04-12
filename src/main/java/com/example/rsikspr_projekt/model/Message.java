package com.example.rsikspr_projekt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Schema(description = "A message stored in the database")
@ToString
@Getter @Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Message ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long Id;

    @Column(nullable = false, length = 500)
    @Schema(description = "Message content", requiredMode = Schema.RequiredMode.REQUIRED, example = "healthy:true")
    private String content;

    @Column(nullable = false, updatable = false)
    @Schema(description = "When was the message recived", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime receivedAt;

    public Message() {
    }

    public Message(String content, LocalDateTime receivedAt) {
        this.content = content;
        this.receivedAt = receivedAt;
    }

}
