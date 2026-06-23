package com.fwishtter.security;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class JwtInterceptor {

    private final ThreadLocal<String> jwt = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> userName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> userType = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> traceId = ThreadLocal.withInitial(() -> null);

    public String getJwt() {
        return jwt.get();
    }

    public void setJwt(String jwt) {
        this.jwt.set(jwt);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserType() {
        return userType.get();
    }

    public void setUserType(String userType) {
        this.userType.set(userType);
    }

    public String getTraceId() {
        return traceId.get();
    }

    public void setTraceId(String traceId) {
        this.traceId.set(traceId);
    }

    public void reset() {
        this.jwt.remove();
        this.userName.remove();
        this.userType.remove();
        this.traceId.remove();
    }

}
