package com.company.properiessetting;

import lombok.Data;

@Data
public class SysProperties {
    @SystemProp("contactbook.profile")
    private String properties;
}
