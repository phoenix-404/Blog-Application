package com.blog.blog_app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String fieldName;
    int fieldValue;

    public ResourceNotFoundException(String resourceName, int fieldValue, String fieldName) {
        super(String.format("%s not found with %s: %l",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
    }
}
