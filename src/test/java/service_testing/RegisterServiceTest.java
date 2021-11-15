package service_testing;

import dao.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;
import service.ClearService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void registerSuccess() throws DataAccessException {
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(new RegisterRequest("RandomUser",
                "password", "random@email.com", "Random", "User", "f"));

        assertNotNull(registerResult.getPersonID());
        assertNotNull(registerResult.getAuthtoken());
        assertTrue(registerResult.getSuccess());
    }

    @Test
    public void registerFailAlreadyExists() throws DataAccessException {
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult1 = registerService.register(new RegisterRequest("RandomUser",
                "password", "random@email.com", "Random", "User", "f"));

        RegisterResult registerResult2 = registerService.register(new RegisterRequest("RandomUser",
                "password", "random@email.com", "Random", "User", "f"));

        assertEquals("Error: Username is already used!", registerResult2.getMessage());
        assertNull(registerResult2.getAuthtoken());
        assertFalse(registerResult2.getSuccess());
    }
}
