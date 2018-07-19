package org.luncert.springauth;

public final class UserProxy {

    private int ttl = 300000; // 5min

    private Identity identity;

    private Long lastAccessTime;

    private Object user;

    protected UserProxy(Identity identity, Object user) {
        this.identity = identity;
        this.user = user;
        this.lastAccessTime =System.currentTimeMillis();
    }

    protected UserProxy(Identity identity, Object user, int ttl) {
        this(identity, user);
        this.ttl = ttl;
    }

    public boolean isExpired() { return System.currentTimeMillis() - ttl >= lastAccessTime; }

    public void updateLastAccessTime() { lastAccessTime = System.currentTimeMillis(); }

    public Identity getIdentity() { return identity; }

    public Object getUser() { return user; }

}