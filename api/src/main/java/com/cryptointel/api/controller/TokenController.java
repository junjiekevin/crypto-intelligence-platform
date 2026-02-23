package com.cryptointel.api.controller;

import com.cryptointel.api.dto.DocumentResponse;
import com.cryptointel.api.dto.TokenCreateRequest;
import com.cryptointel.api.dto.TokenResponse;
import com.cryptointel.api.entity.AnalysisResult;
import com.cryptointel.api.service.AnalysisService;
import com.cryptointel.api.service.DocumentService;
import com.cryptointel.api.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final DocumentService documentService;
    private final AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<TokenResponse> createToken(@Valid @RequestBody TokenCreateRequest request) {
        return new ResponseEntity<>(tokenService.createToken(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TokenResponse>> listTokens() {
        return ResponseEntity.ok(tokenService.listTokens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenResponse> getToken(@PathVariable UUID id) {
        return ResponseEntity.ok(tokenService.getToken(id));
    }

    @PostMapping("/{id}/documents")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(documentService.uploadDocument(id, file), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/analysis")
    public ResponseEntity<List<AnalysisResult>> getAnalysisHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(analysisService.getAnalysisHistory(id));
    }
}
