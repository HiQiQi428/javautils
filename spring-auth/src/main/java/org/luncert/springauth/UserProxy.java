package org.luncert.springauth;

public final class UserProxy {

    private int ttl = 300000; // 5min

    private int accessLevel;

    private Long lastAccessTime;

    private Object user;

    protected UserProxy(int accessLevel, Object user) {
        this.accessLevel = accessLevel;
        this.user = user;
        this.lastAccessTime =System.currentTimeMillis();
    }

    protected UserProxy(int accessLevel, Object user, int ttl) {
        this(accessLevel, user);
        this.ttl = ttl;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - ttl >= lastAccessTime;
    }

    public void updateLastAccessTime() {
        lastAccessTime = System.currentTimeMillis();
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public Object getUser() {
        return user;
    }

}