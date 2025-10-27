package it.sara.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class HighCardApplicationTests {

    @Test
    void contextLoads() {
    }

    @MockitoBean
    private AuthenticationManager authenticationManager;

}
