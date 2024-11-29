package io.dealsplus.authsystem.authentication.repository.mappers;

import io.dealsplus.authsystem.authentication.entity.AccountToken;
import io.dealsplus.authsystem.authentication.repository.tables.AccountTokenRecord;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class AccountTokenMapper {
    public AccountTokenRecord toAccountTokenRecord(AccountToken accountToken) {
        return new AccountTokenRecord(accountToken.getId(), accountToken.getAccountId(), accountToken.getRefreshToken(), new Timestamp(accountToken.getExpiresAt().getTime()));
    }

    public AccountToken toAccount(AccountTokenRecord accountTokenRecord, String accessToken) {
        return new AccountToken(accountTokenRecord.getId(), accountTokenRecord.getAccountId(), accountTokenRecord.getRefreshToken(),accessToken, new Date(accountTokenRecord.getExpiresAt().getTime()));
    }
}
