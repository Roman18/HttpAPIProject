package com.company.properiessetting;

import lombok.Data;

@Data
public class FileProperties {
    @SystemProp("app.service.workmode")
    private String mod;
    @SystemProp("api.base-uri")
    private String uri;
    @SystemProp("file.path")
    private String file;
    @SystemProp("db.dsn")
    private String dsn;
    @SystemProp("db.user")
    private String user;
    @SystemProp("db.password")
    private String password;
}
