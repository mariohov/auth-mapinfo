package br.com.mapinfo.authservice.user.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrivilegeDTO implements Serializable {

    private static final long serialVersionUID = 2010155015539234077L;

    private String id;

    private String name;
}
