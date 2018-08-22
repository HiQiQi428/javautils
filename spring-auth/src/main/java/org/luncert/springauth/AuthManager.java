package org.luncert.springauth;

public class AuthManager {

    public void grant(Identity identity, Object user) {
        AuthInterceptor.grant(identity, user);
    }

    public void grant(Identity identity, Object user, int ttl) {
        AuthInterceptor.grant(identity, user, ttl);
    }

}
