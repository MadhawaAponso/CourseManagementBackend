package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.madhawaa.dto.requestDTO.UserRequestDTO;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;

@ApplicationScoped
public class KeycloakAdminService {

    @ConfigProperty(name = "keycloak.admin.url")
    String keycloakUrl;

    @ConfigProperty(name = "keycloak.admin.realm")
    String realm;

    @ConfigProperty(name = "keycloak.admin.client-id")
    String clientId;

    @ConfigProperty(name = "keycloak.admin.username")
    String adminUsername;

    @ConfigProperty(name = "keycloak.admin.password")
    String adminPassword;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String getAdminToken() {
        String body = "grant_type=password"
                + "&client_id=" + clientId
                + "&username=" + adminUsername
                + "&password=" + adminPassword;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(keycloakUrl + "/realms/master/protocol/openid-connect/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject json = Json.createReader(new StringReader(response.body())).readObject();
            return json.getString("access_token");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get admin token", e);
        }
    }

//    public void createUserInKeycloak(String username, String email, String password) {
//        String token = getAdminToken();
//
//        JsonObject payload = Json.createObjectBuilder()
//                .add("username", username)
//                .add("email", email)
//                .add("enabled", true)
//                .add("credentials", Json.createArrayBuilder().add(
//                        Json.createObjectBuilder()
//                                .add("type", "password")
//                                .add("value", password)
//                                .add("temporary", false)
//                ))
//                .build();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(keycloakUrl + "/admin/realms/" + realm + "/users"))
//                .header("Authorization", "Bearer " + token)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
//                .build();
//
//        try {
//            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create user in Keycloak", e);
//        }
//    }
//public void createUserInKeycloak(UserRequestDTO dto) throws Exception {
//    Keycloak keycloak = getKeycloakInstance();
//
//    UserRepresentation user = new UserRepresentation();
//    user.setUsername(dto.getUsername());
//    user.setEmail(dto.getEmail());
//    user.setEnabled(true);
//    user.setFirstName(dto.getFirstName());
//    user.setLastName(dto.getLastName());
//
//    // Optional: set custom attributes like userId (not visible by default)
//    Map<String, List<String>> attributes = new HashMap<>();
//    attributes.put("userId", List.of(dto.getId() != null ? dto.getId().toString() : "")); // or generate manually if null
//    user.setAttributes(attributes);
//
//    CredentialRepresentation credential = new CredentialRepresentation();
//    credential.setType(CredentialRepresentation.PASSWORD);
//    credential.setValue(dto.getPassword());
//    credential.setTemporary(false);
//
//    user.setCredentials(List.of(credential));
//
//    Response response = keycloak.realm(realm)
//            .users()
//            .create(user);
//
//    if (response.getStatus() != 201) {
//        throw new RuntimeException("Failed to create user in Keycloak: " + response.readEntity(String.class));
//    }
//}
public String createUserInKeycloak(UserRequestDTO dto) {
    UserRepresentation user = new UserRepresentation();
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEnabled(true);

    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(dto.getPassword());
    credential.setTemporary(false);
    user.setCredentials(List.of(credential)); // <-- ✅ THIS IS CRUCIAL

    Response response = getKeycloakInstance()
            .realm("course-management")
            .users()
            .create(user);

    if (response.getStatus() != 201) {
        throw new RuntimeException("Failed to create user in Keycloak: " + response.readEntity(String.class));
    }

    // Extract and return Keycloak user ID from Location header
    String location = response.getHeaderString("Location");
    return location.substring(location.lastIndexOf("/") + 1);
}

    public void updateUserAttribute(String keycloakUserId, String key, String value) {
        Keycloak keycloak = getKeycloakInstance();
        UserResource userResource = keycloak.realm("course-management").users().get(keycloakUserId);
        UserRepresentation user = userResource.toRepresentation();

        Map<String, List<String>> attributes = user.getAttributes();
        if (attributes == null) attributes = new HashMap<>();
        attributes.put(key, List.of(value));
        user.setAttributes(attributes);

        userResource.update(user);
    }


    public String getUserIdByUsername(String username) {
        String token = getAdminToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(keycloakUrl + "/admin/realms/" + realm + "/users?username=" + username))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonArray users = Json.createReader(new StringReader(response.body())).readArray();

            if (users.isEmpty()) {
                throw new RuntimeException("User not found in Keycloak: " + username);
            }

            return users.getJsonObject(0).getString("id");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch userId from Keycloak", e);
        }
    }

//    public void assignRoleToUser(String userId, String roleName) {
//        String token = getAdminToken();
//
//        JsonObject role = Json.createObjectBuilder()
//                .add("name", roleName)
//                .build();
//
//        JsonArray body = Json.createArrayBuilder().add(role).build();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm"))
//                .header("Authorization", "Bearer " + token)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
//                .build();
//
//        try {
//            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to assign role to user in Keycloak", e);
//        }
//    }

    public void assignRoleToUser(String userId, String roleName) {
        try {
            Keycloak keycloak = getKeycloakInstance();

            // Fetch the role representation
            RoleRepresentation role = keycloak.realm(realm)
                    .roles()
                    .get(roleName)
                    .toRepresentation();

            // Assign the role to the user at realm level
            keycloak.realm(realm)
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(List.of(role));
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign role to user in Keycloak", e);
        }
    }

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081") // no /auth anymore in recent versions
                .realm("master") // always use "master" realm for admin-cli login
                .username("madhawa") // ✅ must be an admin user
                .password("5030161") // ✅ must be correct
                .clientId("admin-cli") // ✅ always use this for admin tasks
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }


}
