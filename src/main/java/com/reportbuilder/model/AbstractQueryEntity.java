package com.reportbuilder.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractQueryEntity {

    @Min(1)
    private int queryId;

    @NotBlank
    @Size(max = 120)
    private String query;
}
