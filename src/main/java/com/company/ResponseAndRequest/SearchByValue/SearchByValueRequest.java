package com.company.ResponseAndRequest.SearchByValue;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchByValueRequest implements SearchParent{
    private String value;
}
