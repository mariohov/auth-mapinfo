package br.com.mapinfo.authservice.multitenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static br.com.mapinfo.authservice.multitenant.MultiTenantConstants.DEFAULT_TENANT_ID;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    DataSource defaultDS;

    @Autowired
    ApplicationContext context;

    static Map<String, DataSource> map = new HashMap<>();

    boolean init = false;

    @PostConstruct
    public void load() {
        map.put(DEFAULT_TENANT_ID, defaultDS);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
        if (!init) {
            init = true;
            map.putAll(tenantDataSource.getAll());
        }

        if (map.get(tenantIdentifier) == null) {
            map.put(tenantIdentifier, tenantDataSource.getDataSource(tenantIdentifier));
        }

        return map.get(tenantIdentifier);
    }

}
