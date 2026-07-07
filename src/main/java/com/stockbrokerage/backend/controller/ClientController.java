package com.stockbrokerage.backend.controller;

import com.stockbrokerage.backend.dto.ClientRequest;
import com.stockbrokerage.backend.dto.ClientResponse;
import com.stockbrokerage.backend.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;

    // Create Client Account
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> createClient(
            @Valid @RequestBody ClientRequest request) {

        return ResponseEntity.ok(clientService.createClient(request));
    }

    // Get Client By ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> getClient(@PathVariable Long id) {

        return ResponseEntity.ok(clientService.getClient(id));
    }

    // Get All Clients
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientResponse>> getAllClients() {

        return ResponseEntity.ok(clientService.getAllClients());
    }

    // Delete Client
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {

        return ResponseEntity.ok(clientService.deleteClient(id));
    }
    @GetMapping("/search")
public ResponseEntity<List<ClientResponse>> searchClients(
        @RequestParam String keyword) {

    return ResponseEntity.ok(
            clientService.searchClients(keyword)
    );
}

}