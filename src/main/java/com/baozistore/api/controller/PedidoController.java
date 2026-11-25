package com.baozistore.api.controller;

import com.baozistore.api.model.Pedido;
import com.baozistore.api.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoResposta> listar() {
        return service.listar().stream().map(PedidoResposta::de).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResposta cadastrar(@RequestBody PedidoEntrada entrada) {
        try {
            Pedido pedido = service.cadastrar(entrada.clienteId(), entrada.produtoId(), entrada.quantidade());
            return PedidoResposta.de(pedido);
        } catch (IllegalArgumentException erro) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro.getMessage(), erro);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResposta> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(PedidoResposta::de)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    public record PedidoEntrada(Long clienteId, Long produtoId, Integer quantidade) {
    }

    public record PedidoResposta(Long id, Long clienteId, Long produtoId, Integer quantidade) {

        static PedidoResposta de(Pedido pedido) {
            return new PedidoResposta(
                    pedido.getId(), pedido.getCliente().getId(), pedido.getProduto().getId(), pedido.getQuantidade());
        }
    }
}
