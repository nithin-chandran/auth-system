package io.dealsplus.authsystem.authorization.repos;

import io.dealsplus.authsystem.authorization.entity.Role;
import io.dealsplus.authsystem.authorization.repos.jpa.RoleJpaRepository;
import io.dealsplus.authsystem.authorization.repos.jpa.RolePermissionJpaRepository;
import io.dealsplus.authsystem.authorization.repos.mappers.RoleMapper;
import io.dealsplus.authsystem.authorization.repos.tables.RoleRecord;
import io.dealsplus.authsystem.authorization.role.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PsqlRoleRepository implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final RolePermissionJpaRepository rolePermissionJpaRepository;
    private final RoleMapper roleMapper;

    public PsqlRoleRepository(RoleJpaRepository roleJpaRepository, RolePermissionJpaRepository rolePermissionJpaRepository, RoleMapper roleMapper) {
        this.roleJpaRepository = roleJpaRepository;
        this.rolePermissionJpaRepository = rolePermissionJpaRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Role createRole(Role role) {
        var roleTable = roleJpaRepository.save(this.roleMapper.toRoleRecord(role));
        return roleMapper.toRole(roleTable);
    }

    @Override
    public Role getRole(String roleName, boolean includePermissions) {
        Optional<RoleRecord> roleTable = roleJpaRepository.findByName(roleName);
        if (roleTable.isEmpty()) {
            return null;
        }
        if (includePermissions) {
            var rolePermissions = rolePermissionJpaRepository.findAllByRoleId(roleTable.get().getId());
            return roleMapper.toRole(roleTable.get(), rolePermissions);
        }
        return roleMapper.toRole(roleTable.get());
    }

    @Override
    public Role getRole(Long roleId, boolean includePermissions) {
        Optional<RoleRecord> roleTable = roleJpaRepository.findById(roleId);
        if (roleTable.isEmpty()) {
            return null;
        }
        if (includePermissions) {
            var rolePermissions = rolePermissionJpaRepository.findAllByRoleId(roleTable.get().getId());
            return roleMapper.toRole(roleTable.get(), rolePermissions);
        }
        return roleMapper.toRole(roleTable.get());
    }

    @Override
    public List<Role> getRoles(List<Long> roleIds, boolean includePermissions) {
        List<RoleRecord> roleTables = roleJpaRepository.findAllById(roleIds);
        if (roleTables.isEmpty()) {
            return List.of();
        }
        var filteredRoleIds = roleTables.stream().map(RoleRecord::getId).toList();
        if (includePermissions) {
            var rolePermissions = rolePermissionJpaRepository.findAllByRoleIdIn(filteredRoleIds);
            return roleMapper.toRoles(roleTables, rolePermissions);
        }
        return roleMapper.toRoles(roleTables);
    }

    @Override
    public List<Role> getRoles(boolean includePermissions) {
        List<RoleRecord> roleTables = roleJpaRepository.findAll();
        if (roleTables.isEmpty()) {
            return List.of();
        }
        var filteredRoleIds = roleTables.stream().map(RoleRecord::getId).toList();
        if (includePermissions) {
            var rolePermissions = rolePermissionJpaRepository.findAllByRoleIdIn(filteredRoleIds);
            return roleMapper.toRoles(roleTables, rolePermissions);
        }
        return roleMapper.toRoles(roleTables);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleJpaRepository.deleteById(roleId);
    }

    @Override
    public void addPermissions(Long roleId, List<Long> permissionIds) {
        var rolePermissions = roleMapper.toRolePermissionRecords(roleId, permissionIds);
        rolePermissionJpaRepository.saveAll(rolePermissions);
    }
}
