/*
 *  Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.wso2.carbon.apimgt.user.ctx.builder.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.user.ctx.builder.UserContextBuilder;
import org.wso2.carbon.apimgt.user.ctx.util.UserContextConstants;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.api.ClaimMapping;
import org.wso2.carbon.user.api.UserStoreException;

import java.util.HashMap;
import java.util.Map;

public class OAuthOpaqueUserContextBuilderImpl implements UserContextBuilder {
    private static final Log logger = LogFactory.getLog(OAuthOpaqueUserContextBuilderImpl.class);
    private final String accessToken;

    public OAuthOpaqueUserContextBuilderImpl(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Map<String, Object> getProperties() {
        // todo: introspect the idp and get user claims such as username, organization, and the roles
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

        try {
            ClaimMapping[] claimMappings = PrivilegedCarbonContext.getThreadLocalCarbonContext().getUserRealm()
                    .getClaimManager().getAllClaimMappings();
            // todo
        } catch (UserStoreException e) {
            e.printStackTrace();
        }

        return props;
    }
}
