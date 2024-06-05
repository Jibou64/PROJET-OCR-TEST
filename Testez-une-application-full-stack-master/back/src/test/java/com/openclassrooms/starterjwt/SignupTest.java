package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SignupTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSignupWithValidDetails() {
        // Préparer une requête d'inscription valide
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("Test4@Email.com");
        signupRequest.setPassword("test!1234");
        signupRequest.setFirstName("Test4");
        signupRequest.setLastName("SignUp0");

        // Effectuer l'inscription
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Vérifier que le statut HTTP est OK (200) ou CREATED (201)
        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.CREATED);

        // Vérifier que le corps de la réponse contient un message de succès
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertEquals("User registered successfully!", messageResponse.getMessage());
    }

    @Test
    public void testSignupWithExistingEmail() {
        // Préparer une requête d'inscription avec un email existant
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("yoga@studio.com");
        signupRequest.setPassword("test!1234");
        signupRequest.setFirstName("NeDoitPas");
        signupRequest.setLastName("Apparaître");

        // Effectuer l'inscription
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Vérifier que le statut HTTP est BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Vérifier que le corps de la réponse contient un message d'erreur
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertEquals("Error: Email is already taken!", messageResponse.getMessage());
    }

    @Test
    public void testSignupWithMissingEmail() {
        // Préparer une requête d'inscription avec un champ email manquant
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("test!1234");
        signupRequest.setFirstName("NeDoitPas");
        signupRequest.setLastName("Apparaître");

        // Effectuer l'inscription
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Vérifier que le statut HTTP est BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Vérifier que le corps de la réponse contient un message d'erreur
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertEquals("Error: Email is required!", messageResponse.getMessage());
    }

    @Test
    public void testSignupWithMissingPassword() {
        // Préparer une requête d'inscription avec un champ mot de passe manquant
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("yoga@studio.com");
        signupRequest.setFirstName("NeDoitPas");
        signupRequest.setLastName("Apparaître");

        // Effectuer l'inscription
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Vérifier que le statut HTTP est BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Vérifier que le corps de la réponse contient un message d'erreur
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertFalse(messageResponse.getMessage().isEmpty());
    }

    @Test
    public void testSignupWithMissingFisrtName() {
        // Préparer une requête d'inscription avec un champ nom d'utilisateur manquant
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("yoga@studio.com");
        signupRequest.setPassword("test!1234");
        signupRequest.setLastName("Apparaître");

        // Effectuer l'inscription
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Vérifier que le statut HTTP est BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Vérifier que le corps de la réponse contient un message d'erreur
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertFalse(messageResponse.getMessage().isEmpty());
    }

    @Test
    public void testSignupWithMissingLastName() {
        // Préparer une requête d'inscription avec un champ nom d'utilisateur manquant
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("yoga@studio.com");
        signupRequest.setPassword("test!1234");
        signupRequest.setFirstName("NeDoitPas");

        // Effectuer l'inscription
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Vérifier que le statut HTTP est BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Vérifier que le corps de la réponse contient un message d'erreur
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertFalse(messageResponse.getMessage().isEmpty());
    }
}
