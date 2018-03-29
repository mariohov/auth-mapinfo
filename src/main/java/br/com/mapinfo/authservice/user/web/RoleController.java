package br.com.mapinfo.authservice.user.web;


import br.com.mapinfo.authservice.user.RoleService;
import br.com.mapinfo.authservice.user.web.dto.RoleDTO;
import br.com.mapinfo.authservice.user.web.dto.RoleMapper;
import br.com.mapinfo.authservice.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController extends BaseController {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/roles")
    public List<RoleDTO> findAll() {
        return roleMapper.fromRoles(roleService.findAll());
    }
}
