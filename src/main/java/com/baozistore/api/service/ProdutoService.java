package com.baozistore.api.service;

import com.baozistore.api.model.Produto;
import com.baozistore.api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto cadastrar(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public boolean excluir(Long id) {
        Optional<Produto> registro = repository.findById(id);
        if (registro.isEmpty()) {
            return false;
        }

        repository.delete(registro.get());
        return true;
    }
}
