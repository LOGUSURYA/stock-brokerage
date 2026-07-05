package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.ClientRequest;
import com.stockbrokerage.backend.dto.ClientResponse;
import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.User;
import com.stockbrokerage.backend.enums.AccountStatus;
import com.stockbrokerage.backend.repository.ClientRepository;
import com.stockbrokerage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    // Create Client Account
    public ClientResponse createClient(ClientRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (clientRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Client Account Already Exists");
        }

        if (clientRepository.findByPanNumber(request.getPanNumber()).isPresent()) {
            throw new RuntimeException("PAN Number Already Exists");
        }

        if (clientRepository.findByAadhaarNumber(request.getAadhaarNumber()).isPresent()) {
            throw new RuntimeException("Aadhaar Number Already Exists");
        }

        ClientAccount client = ClientAccount.builder()
                .user(user)
                .panNumber(request.getPanNumber())
                .aadhaarNumber(request.getAadhaarNumber())
                .address(request.getAddress())
                .balance(request.getBalance())
                .status(AccountStatus.ACTIVE)
                .createdDate(LocalDate.now())
                .build();

        clientRepository.save(client);

        return mapToResponse(client);
    }

    // Get Client By ID
    public ClientResponse getClient(Long id) {

        ClientAccount client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client Not Found"));

        return mapToResponse(client);
    }

    // Get All Clients
    public List<ClientResponse> getAllClients() {

        return clientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
   // Search Clients
public List<ClientResponse> searchClients(String keyword) {

    return clientRepository
            .findByUser_NameContainingIgnoreCaseOrUser_EmailContainingIgnoreCase(
                    keyword,
                    keyword
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
}
    // Delete Client
    public String deleteClient(Long id) {

        ClientAccount client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client Not Found"));

        clientRepository.delete(client);

        return "Client Deleted Successfully";
    }

    // Convert Entity -> DTO
    private ClientResponse mapToResponse(ClientAccount client) {

        return ClientResponse.builder()
                .id(client.getId())
                .userName(client.getUser().getName())
                .email(client.getUser().getEmail())
                .panNumber(client.getPanNumber())
                .aadhaarNumber(client.getAadhaarNumber())
                .address(client.getAddress())
                .balance(client.getBalance())
                .status(client.getStatus())
                .createdDate(client.getCreatedDate())
                .build();
    }

}