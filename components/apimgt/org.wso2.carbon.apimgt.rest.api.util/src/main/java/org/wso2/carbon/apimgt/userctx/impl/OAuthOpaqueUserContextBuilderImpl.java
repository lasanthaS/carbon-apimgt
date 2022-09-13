package org.wso2.carbon.apimgt.userctx.impl;

import org.wso2.carbon.apimgt.userctx.UserContextBuilder;
import org.wso2.carbon.apimgt.userctx.UserContextConstants;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.api.UserStoreException;

import java.util.HashMap;
import java.util.Map;

public class OAuthOpaqueUserContextBuilderImpl implements UserContextBuilder {
    private final String accessToken;

    public OAuthOpaqueUserContextBuilderImpl(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Map<String, Object> getProperties() {
        PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext.getThreadLocalCarbonContext();

        Map<String, Object> props = new HashMap<>();
        String username = carbonContext.getUsername();
        props.put(UserContextConstants.ATTRIB_USERNAME, username);
        props.put(UserContextConstants.ATTRIB_ORGANIZATION, carbonContext.getTenantDomain()); // todo:

        try {
            String[] roles = carbonContext.getUserRealm().getUserStoreManager().getRoleListOfUser(username);
            props.put(UserContextConstants.ATTRIB_ROLES, roles);
        } catch (UserStoreException e) {
            e.printStackTrace();
        }
        return props;
    }

    @Override
    public String getClaim(String claimUri) {
        try {
            Claim claim = PrivilegedCarbonContext.getThreadLocalCarbonContext().getUserRealm().getClaimManager()
                    .getClaim(claimUri);
            if (claim != null) {
                return claim.getValue();
            }
        } catch (UserStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
}
