package com.reportbuilder.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SingleQuery {
    @Min(1)
    private int queryId;

    @NotBlank
    @Size(max = 120)
    private String query;
}
