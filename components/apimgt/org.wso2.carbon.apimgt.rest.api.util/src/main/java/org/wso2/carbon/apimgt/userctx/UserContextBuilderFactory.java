package org.wso2.carbon.apimgt.userctx;

import org.wso2.carbon.apimgt.userctx.impl.OAuthJWTUserContextBuilderImpl;
import org.wso2.carbon.apimgt.userctx.impl.OAuthOpaqueUserContextBuilderImpl;

public class UserContextBuilderFactory {
    public static UserContextBuilder createUserContextBuilder(String accessToken) {
        if (accessToken.contains(UserContextConstants.DOT)) {
            return new OAuthJWTUserContextBuilderImpl();
        }
        return new OAuthOpaqueUserContextBuilderImpl(accessToken);
    }
}
