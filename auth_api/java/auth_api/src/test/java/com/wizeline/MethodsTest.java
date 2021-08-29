package com.wizeline;

import static com.wizeline.Methods.*;

import com.wizeline.model.User;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.wizeline.exception.WizeLineException;
import com.wizeline.exception.UnauthorizedException;
/**
 * Unit test for simple App.
 */
public class MethodsTest extends TestCase{

    public MethodsTest( String testName ){
        super( testName );
    }

    public static Test suite(){
        return new TestSuite( MethodsTest.class );
    }

    public void testGenerateToken(){
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4ifQ.D6AcZ1kVOJaa7pzynF3rd7qI3I7memlYR8glEsF9ryw", Methods.generateToken("admin", "secret"));
    }

    public void testGenerateTokenNotValidUser(){
        try {
            Methods.generateToken("no-user", "no-pass");
        } catch (WizeLineException wlExp) {
            assertEquals("User was NOT Found.", wlExp.getMessage());
        }
    }

    private User getUser() {
        User user = new User();
        user.setUsername("dummy-user");
        user.setPassword("dummy-pass");
        user.setSalt("dummy-salt");
        user.setRole("dummy-role");
        return user;
    }

    public void testValidateCredentialsFalse() {
        User user = getUser();
        assertFalse(Methods.validateCredentials(user, "not-dummy-pass"));
    }

    public void testAccessData(){
        assertEquals("You are under protected data", Methods.accessData("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4ifQ.D6AcZ1kVOJaa7pzynF3rd7qI3I7memlYR8glEsF9ryw"));
    }

    public void testAccessDataUnauthorizedException(){
        try {
            Methods.accessData("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4ifQ.D6AcZ1kVOJaa7pzynF3rd7qI3I7memlYR8glEsF9ryy");
        } catch (UnauthorizedException unauthExp) {
            assertTrue(unauthExp.getMessage().contains("Unauthenticated"));
        }
    }
}
