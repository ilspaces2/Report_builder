package com.reportbuilder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnReport {

    private String title;

    private String type;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int size;
}
