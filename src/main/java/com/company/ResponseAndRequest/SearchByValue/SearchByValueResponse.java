package com.company.ResponseAndRequest.SearchByValue;

import com.company.ResponseAndRequest.GetAll.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchByValueResponse {
    private List<UserResponse> contacts;
    private String status;
    private String error;
}
