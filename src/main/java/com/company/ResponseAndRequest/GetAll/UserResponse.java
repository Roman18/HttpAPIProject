package com.company.ResponseAndRequest.GetAll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String value;
    private String name;
    private String type;
    private int id;


}
