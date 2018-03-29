package br.com.mapinfo.authservice.company.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyDTO implements Serializable {

    private static final long serialVersionUID = -1633860713984415778L;

    private Long id;
    private String name;
    private String registration;
    private String tenant;

}
