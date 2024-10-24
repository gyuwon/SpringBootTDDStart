package test.commerce.api.shopper.issuetoken;

import commerce.command.CreateShopperCommand;
import commerce.query.IssueShopperToken;
import commerce.result.AccessTokenCarrier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import test.commerce.Base64Url;
import test.commerce.api.CommerceApiTest;

import static org.assertj.core.api.Assertions.assertThat;
import static test.commerce.EmailGenerator.generateEmail;
import static test.commerce.PasswordGenerator.generatePassword;
import static test.commerce.UsernameGenerator.generateUsername;

@CommerceApiTest
@DisplayName("POST /shopper/issueToken")
public class POST_specs {

    @Test
    void 올바르게_요청하면_200_OK_상태코드를_반환한다(
        @Autowired TestRestTemplate client
    ) {
        // Arrange
        String email = generateEmail();
        String password = generatePassword();

        client.postForEntity(
            "/shopper/signUp",
            new CreateShopperCommand(email, generateUsername(), password),
            Void.class
        );

        // Act
        ResponseEntity<Void> response = client.postForEntity(
            "/shopper/issueToken",
            new IssueShopperToken(email, password),
            Void.class
        );

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void 올바르게_요청하면_접근토큰을_반환한다(
        @Autowired TestRestTemplate client
    ) {
        // Arrange
        String email = generateEmail();
        String password = generatePassword();

        client.postForEntity(
            "/shopper/signUp",
            new CreateShopperCommand(email, generateUsername(), password),
            Void.class
        );

        // Act
        ResponseEntity<AccessTokenCarrier> response = client.postForEntity(
            "/shopper/issueToken",
            new IssueShopperToken(email, password),
            AccessTokenCarrier.class
        );

        // Assert
        AccessTokenCarrier body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.accessToken()).isNotBlank();
        String[] parts = body.accessToken().split("\\.");
        assertThat(parts).hasSize(3).allMatch(Base64Url::isBase64Url);
    }

    @Test
    void 존재하지_않는_이메일이_사용되면_400_Bad_Request_상태코드를_반환한다(
        @Autowired TestRestTemplate client
    ) {
        // Act
        ResponseEntity<Void> response = client.postForEntity(
            "/shopper/issueToken",
            new IssueShopperToken(generateEmail(), generatePassword()),
            Void.class
        );

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void 잘못된_비밀번호가_사용되면_400_Bad_Request_상태코드를_반환한다(
        @Autowired TestRestTemplate client
    ) {
        // Arrange
        String email = generateEmail();
        String password = generatePassword();
        String wrongPassword = generatePassword();

        client.postForEntity(
            "/shopper/signUp",
            new CreateShopperCommand(email, generateUsername(), password),
            Void.class
        );

        // Act
        ResponseEntity<Void> response = client.postForEntity(
            "/shopper/issueToken",
            new IssueShopperToken(email, wrongPassword),
            Void.class
        );

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }
}
