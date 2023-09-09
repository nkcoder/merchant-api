package my.playground.merchantapi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
@Getter
@RequiredArgsConstructor
public class CategoryEntity {
    @Id
    @Setter
    private Long categoryId;
    private final String categoryName;
    private final String description;
}

