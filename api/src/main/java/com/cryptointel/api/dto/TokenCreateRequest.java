package com.cryptointel.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenCreateRequest {
    @NotBlank(message = "Token name is required")
    private String name;
    
    @NotBlank(message = "Token symbol is required")
    private String symbol;
}
