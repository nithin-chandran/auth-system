package io.dealsplus.authsystem.authentication.token;

import io.dealsplus.authsystem.authentication.models.AccountAccess;
import io.dealsplus.authsystem.authentication.token.exception.InvalidTokenException;
import io.dealsplus.authsystem.authentication.token.exception.TokenExpiredException;
import io.dealsplus.authsystem.authentication.entity.Account;
import io.dealsplus.authsystem.authentication.entity.AccountToken;
import io.dealsplus.authsystem.authentication.repository.AccountRepository;
import io.dealsplus.authsystem.authentication.token.models.Token;
import io.dealsplus.authsystem.configs.ApplicationConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtTokenManager implements TokenManager {

    private final AccountRepository accountRepository;

    private final ApplicationConfig appConfig;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenManager.class);

    @Autowired
    public JwtTokenManager(AccountRepository accountRepository, ApplicationConfig appConfig) {
        this.accountRepository = accountRepository;
        this.appConfig = appConfig;
    }

    @Override
    public Token generateToken(Account account) {
        String secretKeyString = appConfig.getJwtSecret();
        long expirationTime = getMinuteInMilliseconds(appConfig.getJwtAccessExpiryInMinutes());
        var refreshTokenExpiredAt = new Date(System.currentTimeMillis() + getMinuteInMilliseconds(appConfig.getJwtRefreshExpiryInMinutes()));
        Map<String, Object> claims = new HashMap<>();
        if (StringUtils.isNotEmpty(account.getPhone())) {
            claims.put("phone", account.getPhone());
        }
        if (StringUtils.isNotEmpty(account.getEmail())) {
            claims.put("email", account.getEmail());
        }

        Key secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        var accessToken = Jwts.builder().claims(claims).
                subject(account.getId().toString()).

                issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expirationTime)).
                signWith(secretKey).
                compact();
        var refreshToken = UUID.randomUUID().toString();
        this.accountRepository.addToken(new AccountToken(null, account.getId(), refreshToken, accessToken, refreshTokenExpiredAt));
        return new Token(accessToken, refreshToken);
    }

    @Override
    public AccountAccess validateAccessToken(String accessToken) {
        String secretKeyString = appConfig.getJwtSecret();
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        try {
            JwtParser jwtParser = Jwts.parser()
                    .verifyWith(secretKey)
                    .build();
            Claims claims = (Claims) jwtParser.parse(accessToken).getPayload();

            if (claims.getExpiration().before(new Date())) {
                throw new TokenExpiredException("Token expired");
            }
            long accountId;
            try {
                accountId = Long.parseLong(claims.getSubject());
            } catch (NumberFormatException e) {
                logger.error("invalid account id in the token");
                throw new InvalidTokenException("invalid token");
            }
            var email = claims.get("email", String.class);
            var phone = claims.get("phone", String.class);
            return new AccountAccess(accountId, email, phone);

        } catch (JwtException e) {
            logger.error(e.getMessage());
            throw new InvalidTokenException("Token is invalid");
        }
    }

    private long getMinuteInMilliseconds(long min) {
        return min * 60 * 1000;
    }
}
