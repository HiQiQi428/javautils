package org.luncert.springauth;

public class AuthManager {

    public void grant(int accessLevel, Object user) {
        AuthInterceptor.grant(accessLevel, user);
    }

    public void grant(int accessLevel, Object user, int ttl) {
        AuthInterceptor.grant(accessLevel, user, ttl);
    }

}
