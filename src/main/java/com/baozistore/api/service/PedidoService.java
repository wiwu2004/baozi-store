package com.baozistore.api.service;

import com.baozistore.api.model.Pedido;
import com.baozistore.api.repository.PedidoRepository;
import com.baozistore.api.model.Cliente;
import com.baozistore.api.model.Produto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository repository, ClienteService clienteService, ProdutoService produtoService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public List<Pedido> listar() {
        return repository.findAll();
    }

    @Transactional
    public Pedido cadastrar(Long clienteId, Long produtoId, Integer quantidade) {
        if (clienteId == null || produtoId == null || quantidade == null || quantidade < 1) {
            throw new IllegalArgumentException("Informe clienteId, produtoId e quantidade positiva.");
        }

        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        Produto produto = produtoService.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            throw new IllegalArgumentException("Produto indisponível.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(quantidade);
        return repository.save(pedido);
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public boolean excluir(Long id) {
        Optional<Pedido> registro = repository.findById(id);
        if (registro.isEmpty()) {
            return false;
        }

        repository.delete(registro.get());
        return true;
    }
}
