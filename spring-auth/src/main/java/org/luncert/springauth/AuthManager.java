package org.luncert.springauth;

public class AuthManager {

    public void grant(int accessLevel, Object user) {
        if (accessLevel < 0 || user == null) {
            throw new IllegalArgumentException("accessLevel < 0 or user is null");
        }
        AuthInterceptor.grant(accessLevel, user);
    }

    public void grant(int accessLevel, Object user, int ttl) {
        AuthInterceptor.grant(accessLevel, user, ttl);
    }

}
