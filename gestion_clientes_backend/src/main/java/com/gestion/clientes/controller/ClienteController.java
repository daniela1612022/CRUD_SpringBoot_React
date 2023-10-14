package com.gestion.clientes.controller;

import com.gestion.clientes.exception.ResourceNotFoundException;
import com.gestion.clientes.model.Cliente;
import com.gestion.clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin( origins = "http://localhost:3000")
@RestController
@RequestMapping ("/api/v1")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Mostrar lista de clientes (clienteRepository)
    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    // Guardar cliente en la base de datos (guardar en la lista)
    @PostMapping("/clientes")
    public Cliente guardarCliente(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    // Buscar cliente por id
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> listarClientePorId (@PathVariable Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ups! , El cliente con ese ID no existe : " + id));
        return ResponseEntity.ok(cliente);
    }

    // Actualizacion de cliente por busqueda de id
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id , @RequestBody Cliente clienteRequest){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ups! , El cliente con ese ID no existe : " + id));

        cliente.setNombre(clienteRequest.getNombre());
        cliente.setApellido(clienteRequest.getApellido());
        cliente.setEmail(clienteRequest.getEmail());

        Cliente clienteActualizado = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteActualizado);

    }

    // Borrar cliente por busqueda de id
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String , Boolean>> eliminarCliente (@PathVariable Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ups! , El cliente con ese ID no existe : " + id));

        clienteRepository.delete(cliente);
        Map<String , Boolean> response = new HashMap<>();
        response.put("deleted" , Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

}
