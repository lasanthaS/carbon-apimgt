package org.wso2.carbon.apimgt.userctx;

import java.util.Arrays;
import java.util.Map;

public class UserContext {
    private static final ThreadLocal<UserContext> currentUserContext = ThreadLocal.withInitial(UserContext::new);

    private String username;
    private String organization;
    private String[] roles;
    private UserContextBuilder builder;

    private UserContext() {
    }

    private UserContext(UserContextBuilder builder) {
        this.builder = builder;

        // build the UserContext object
        Map<String, Object> properties = builder.getProperties();
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            switch (entry.getKey()) {
                case UserContextConstants.ATTRIB_USERNAME:
                    this.username = (String) properties.get(UserContextConstants.ATTRIB_USERNAME);
                    break;
                case UserContextConstants.ATTRIB_ORGANIZATION:
                    this.organization = (String) properties.get(UserContextConstants.ATTRIB_ORGANIZATION);
                    break;
                case UserContextConstants.ATTRIB_ROLES:
                    this.roles = (String[]) properties.get(UserContextConstants.ATTRIB_ROLES);
                    break;
            }
        }
    }

    public static void initThreadLocalUserContext(UserContextBuilder builder) {
        currentUserContext.set(new UserContext(builder));
    }

    public static UserContext getThreadLocalUserContext() {
        return currentUserContext.get();
    }

    public String getUsername() {
        return username;
    }

    public String getOrganization() {
        return organization;
    }

    public String[] getRoles() {
        return roles;
    }

    public String getClaim(String claimUri) {
        return this.builder.getClaim(claimUri);
    }

    public boolean hasRole(String roleName) {
        return Arrays.asList(roles).contains(roleName);
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "username='" + username + '\'' +
                ", organization='" + organization + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}

