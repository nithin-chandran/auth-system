package io.dealsplus.authsystem.authentication.token.exception;

public class TokenExpiredException extends InvalidTokenException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
