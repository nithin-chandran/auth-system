package io.dealsplus.authsystem.authorization.repos.mappers;

import io.dealsplus.authsystem.authorization.repos.tables.AccountRoleRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountRoleMapper {

    public List<AccountRoleRecord> toAccountRoleRecords(Long accountId, List<Long> roleIds) {
        return roleIds
                        .stream()
                        .map(roleId -> new AccountRoleRecord(null, accountId, roleId))
                        .toList();
    }
}
