package org.wso2.carbon.apimgt.userctx;

import java.util.Map;

public interface UserContextBuilder {
    Map<String, Object> getProperties();

    String getClaim(String claimUri);
}
