package com.company.Services;


import lombok.Data;

@Data
public class Contact {
    private Integer id;
    private String name;
    private String contact;
    private TypeContact type;

  public  enum TypeContact{EMAIL,PHONE}
}
