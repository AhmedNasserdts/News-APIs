package com.upwork.newsports.model;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Set;

@Document("users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
        @Id
        private String id;
        private String name;
        @Indexed(unique = true)
        private String email;
        private String password; // BCrypt
        private Set<String> roles; // e.g., ROLE_MAIN_ADMIN, ROLE_ASSISTANT_ADMIN, ROLE_USER
        private boolean active = true;
        private LocalDateTime createdAt;
}
