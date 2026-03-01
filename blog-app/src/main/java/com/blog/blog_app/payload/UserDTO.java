package com.blog.blog_app.payload;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Integer userId;

//    @NotEmpty ensures that a field is not null and also not empty,
//    meaning it must contain at least one element (for collections) or
//    at least one character (for strings)
    @NotEmpty
    @Size(min =4, message = "Username must be min of 4 characters ")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

//    @NotBlank applies only to strings and ensures they are not null,
//    empty and contain at least one non-whitespace character
//    (i.e., spaces alone are not allowed).
    @NotBlank
    @Size(min = 3, max=10,message = "Password must be min of 3 and max 10 characters")
    private String password;

//    @NotNull ensures that a field is not null but allows empty values
//    (e.g., an empty string or an empty collection)
    @NotNull
    private String about;

}
