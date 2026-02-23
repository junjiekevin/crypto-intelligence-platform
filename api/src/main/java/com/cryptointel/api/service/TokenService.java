package com.cryptointel.api.service;

import com.cryptointel.api.dto.TokenCreateRequest;
import com.cryptointel.api.dto.TokenResponse;
import com.cryptointel.api.entity.Token;
import com.cryptointel.api.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public TokenResponse createToken(TokenCreateRequest request) {
        Token token = Token.builder()
                .name(request.getName())
                .symbol(request.getSymbol())
                .build();
        Token saved = tokenRepository.save(token);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TokenResponse> listTokens() {
        return tokenRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TokenResponse getToken(UUID id) {
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Token not found with id: " + id));
        return mapToResponse(token);
    }

    private TokenResponse mapToResponse(Token token) {
        return TokenResponse.builder()
                .id(token.getId())
                .name(token.getName())
                .symbol(token.getSymbol())
                .createdAt(token.getCreatedAt())
                .updatedAt(token.getUpdatedAt())
                .build();
    }
}
