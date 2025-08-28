package com.upwork.newsports.model;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("subscriptions")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Subscription {
    @Id private String id;
    @Indexed private String userId;
    private String plan; // MONTHLY / YEARLY
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // ACTIVE / EXPIRED
    private String method; // MANUAL / PAYPAL_SANDBOX (لاحقاً)
}
