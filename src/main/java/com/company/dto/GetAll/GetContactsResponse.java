package com.company.dto.GetAll;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetContactsResponse {
    private List<GetContacts> contacts;
    private String status;
    private String error;

    @Data
    public static class GetContacts {
        private String value;
        private String name;
        private String type;
        private Integer id;
    }
}
