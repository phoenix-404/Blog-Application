package com.blog.blog_app.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Integer categoryId;

    @NotEmpty(message = "The Title should not be Empty")
    private String categoryTitle;

    @NotNull
    private String categoryDescription;

}
