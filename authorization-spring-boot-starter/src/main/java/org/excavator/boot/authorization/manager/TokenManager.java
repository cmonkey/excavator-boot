package org.excavator.boot.authorization.manager;

import org.excavator.boot.authorization.model.Token;

public interface TokenManager {

    Token createToken(long customerId);

    boolean checkToken(Token token);

    Token getToken(String authorization);

    void deleteToken(String token);
}