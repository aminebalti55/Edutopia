package com.example.edutopia_res.Services;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;


public class KeycloakClient {
    private static final String SERVER_URL = "http://localhost:8080/";
    private static final String REALM = "Edutopia";
    private static final String CLIENT_ID = "Edutopia_Restaurant";
    private static final String CLIENT_SECRET = "1gzkiN66QXmxciY3jwF5EcJjTXyo8vxc";

    private Keycloak keycloak;

    public KeycloakClient() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();
    }

    public Keycloak getKeycloak() {
        return keycloak;
    }

    public RoleRepresentation getRoleByName(String roleName) {
        Keycloak keycloak = getKeycloak();
        RealmResource realmResource = keycloak.realm(getRealm());
        RolesResource rolesResource = realmResource.roles();

        List<RoleRepresentation> roles = rolesResource.list();
        for (RoleRepresentation role : roles) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }

        return null;
    }
    public String getRealm() {
        return "Edutopia"; // replace with the name of your Keycloak realm
    }
}
