package com.upwork.newsports.model;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("logs")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Log {

    
    @Id private String id;
    private String type; // LOGIN, NEWS_CREATE, API_PULL ...
    private String actorId; // user/admin id
    private String details;
    private Instant ts;
}
