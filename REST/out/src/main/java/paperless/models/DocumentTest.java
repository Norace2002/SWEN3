package paperless.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;


import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    //To validate the document entity
    private Validator validator;

    //Before each test - a validation object ist created
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenTitleIsNull_thenValidationFails() {
        Document document = new Document();
        document.setId(UUID.randomUUID());
        document.setTitle(null); // Invalid value

        Set<ConstraintViolation<Document>> violations = validator.validate(document);

        assertFalse(violations.isEmpty());
    }
}

