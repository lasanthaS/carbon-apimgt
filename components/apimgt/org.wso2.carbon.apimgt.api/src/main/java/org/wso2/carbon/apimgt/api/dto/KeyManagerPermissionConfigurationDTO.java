package org.wso2.carbon.apimgt.api.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *KeyManagerPermissionConfiguration model
 */
public class KeyManagerPermissionConfigurationDTO implements Serializable {

    private String permissionType = null;
    private List<String> roles = new ArrayList<String>();

    public KeyManagerPermissionConfigurationDTO () {
        this.setPermissionType("PUBLIC");
    }

    public KeyManagerPermissionConfigurationDTO(String permissionType, List<String> roles) {
        this.permissionType = permissionType;
        this.roles = roles;
    }

    public String getPermissionType () {
        return permissionType;
    }

    public void setPermissionType (String permissionType) {
        this.permissionType = permissionType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        if (roles == null) {
            return;
        }
        this.roles = roles;
    }
}
