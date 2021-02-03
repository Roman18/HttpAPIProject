package com.company.ResponseAndRequest.GetAll;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetAllResponse {
    private List<UserResponse> contacts;
    private String status;
    private String error;
}
