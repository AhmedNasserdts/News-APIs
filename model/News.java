package com.upwork.newsports.model;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Document("news")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class News {
    @Id private String id;
    private String title;
    private String content;
    private String imageUrl;
    private String category; // football, tennis...
    private String source;   // API or MANUAL
    @Indexed private Instant publishedAt;
    @Indexed(unique = true) private String dedupKey; // مثلاً title+publishedAt+source
}
