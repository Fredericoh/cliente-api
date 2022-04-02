package com.clienteapi.service;

import com.clienteapi.model.Cliente;
import com.clienteapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Iterable<Cliente> listar(Pageable pageable){
        return clienteRepository.findAll(pageable);
    }

    @Transactional
    public Cliente inserir(Cliente cliente){
        cliente.setId (null);
        return clienteRepository.save (cliente);
    }

    public Cliente pesquisarPorId(Long id){

        return clienteRepository.findById (id).orElseThrow (()-> new EmptyResultDataAccessException(String.format("Nenhum cliente cadastrado com o id %s!", id), 1));
    }

    @Transactional
    public Cliente alterar(Cliente cliente, Long id){
        Cliente clientePesquisado = pesquisarPorId (id);

        BeanUtils.copyProperties (cliente, clientePesquisado, "id");

        return clienteRepository.save (clientePesquisado);
    }

    @Transactional
    public void excluir(Long id){

        clienteRepository.deleteById (id);
    }

}
