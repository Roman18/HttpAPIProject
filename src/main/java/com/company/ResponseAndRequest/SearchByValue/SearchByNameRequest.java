package com.company.ResponseAndRequest.SearchByValue;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SearchByNameRequest implements SearchParent{
    private String name;
}
