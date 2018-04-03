package br.com.mapinfo.authservice.multitenant;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "EDATASOURCECONFIG")
public class DataSourceConfig implements Serializable {

    private static final long serialVersionUID = -2563249947782765508L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String username;

    @Column
    private String password;

    @Column(nullable = false)
    private String driverClassName;

    @Column(nullable = false)
    private Boolean initialize;

    @Column(nullable = false)
    private String dialect;
}
