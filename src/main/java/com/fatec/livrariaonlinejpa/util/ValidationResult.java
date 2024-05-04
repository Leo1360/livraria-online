package com.fatec.livrariaonlinejpa.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
    private boolean valid;
    private String errorText;
}
