package com.training.tinelli.sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TokenDTO {
    private String username;
    private String token;
}
