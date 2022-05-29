package com.vipsoftcom.vsclientes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vipsoftcom.vsclientes.dto.ClientDTO;
import com.vipsoftcom.vsclientes.entities.Client;
import com.vipsoftcom.vsclientes.repositories.ClientRepository;
import com.vipsoftcom.vsclientes.services.exceptions.ServiceEntityNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		List<Client> list = repository.findAll();
		return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ServiceEntityNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}

	public ClientDTO insert(ClientDTO entityDTO) {
		Client entity = new Client(); 
		entity.setName(entityDTO.getName());
		entity.setCpf(entityDTO.getCpf());
		entity.setIncome(entityDTO.getIncome());
		entity.setBirthDate(entityDTO.getBirthDate());
		entity.setChildren(entityDTO.getChildren());
		repository.save(entity);
		return new ClientDTO(entity);
	}
}
