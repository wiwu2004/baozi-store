package com.baozistore.api.service;

import com.baozistore.api.model.Cliente;
import com.baozistore.api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente cadastrar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public boolean excluir(Long id) {
        Optional<Cliente> registro = repository.findById(id);
        if (registro.isEmpty()) {
            return false;
        }

        repository.delete(registro.get());
        return true;
    }
}
