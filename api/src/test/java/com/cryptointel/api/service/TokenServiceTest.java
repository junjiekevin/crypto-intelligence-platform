package com.cryptointel.api.service;

import com.cryptointel.api.dto.TokenCreateRequest;
import com.cryptointel.api.dto.TokenResponse;
import com.cryptointel.api.entity.Token;
import com.cryptointel.api.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    private Token token;

    @BeforeEach
    void setUp() {
        token = Token.builder()
                .id(UUID.randomUUID())
                .name("Ethereum")
                .symbol("ETH")
                .build();
    }

    @Test
    void createToken_ShouldReturnResponse() {
        TokenCreateRequest request = TokenCreateRequest.builder()
                .name("Ethereum")
                .symbol("ETH")
                .build();

        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        TokenResponse response = tokenService.createToken(request);

        assertNotNull(response);
        assertEquals(token.getName(), response.getName());
        assertEquals(token.getSymbol(), response.getSymbol());
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void getToken_ShouldReturnResponse() {
        when(tokenRepository.findById(token.getId())).thenReturn(Optional.of(token));

        TokenResponse response = tokenService.getToken(token.getId());

        assertNotNull(response);
        assertEquals(token.getName(), response.getName());
        verify(tokenRepository, times(1)).findById(token.getId());
    }

    @Test
    void getToken_ShouldThrowException_WhenNotFound() {
        UUID id = UUID.randomUUID();
        when(tokenRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tokenService.getToken(id));
    }
}
