package io.dealsplus.authsystem.authorization.account.repository;

import java.util.List;

public interface AccountRoleRepository {
    void addRoles(Long accountId, List<Long> roleIds);
    List<Long> getRoles(Long accountId);
}
