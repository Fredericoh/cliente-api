package com.clienteapi.resource;

import com.clienteapi.model.Cliente;
import com.clienteapi.repository.ClienteRepository;
import com.clienteapi.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteResource {

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    @GetMapping
    public @ResponseBody
    List<Cliente> list() {
        return clienteRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void inserir(@RequestBody @Valid Cliente cliente, HttpServletResponse response){

        cliente = clienteService.inserir (cliente);

        response.setHeader (HttpHeaders.LOCATION, ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri().toString());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente pesquisarPorId(@PathVariable("id") Long id){

        return clienteService.pesquisarPorId (id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente alterar(@RequestBody @Valid Cliente cliente, @PathVariable Long id){

        return clienteService.alterar (cliente, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){

        clienteService.excluir (id);
    }
}
