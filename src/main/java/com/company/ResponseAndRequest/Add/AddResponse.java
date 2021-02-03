package com.company.ResponseAndRequest.Add;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddResponse {
    private String status;
    private String error;

}
