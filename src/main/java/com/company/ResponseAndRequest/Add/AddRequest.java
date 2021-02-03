package com.company.ResponseAndRequest.Add;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRequest {
    private String type;
    private String value;
    private String name;

}
