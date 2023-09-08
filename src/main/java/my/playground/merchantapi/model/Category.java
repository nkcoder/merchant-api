package my.playground.merchantapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
@Getter
@RequiredArgsConstructor
public class Category {
    @Id
    @Setter
    private Long categoryID;
    private final String categoryName;
    private final String description;
}

