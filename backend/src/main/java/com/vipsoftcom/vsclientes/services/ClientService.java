package com.vipsoftcom.vsclientes.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vipsoftcom.vsclientes.dto.ClientDTO;
import com.vipsoftcom.vsclientes.entities.Client;
import com.vipsoftcom.vsclientes.repositories.ClientRepository;
import com.vipsoftcom.vsclientes.services.exceptions.DatabaseException;
import com.vipsoftcom.vsclientes.services.exceptions.ServiceEntityNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPages(Pageable pageable) {
		Page<Client> list = repository.findAll(pageable);
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ServiceEntityNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}

	@Transactional
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

	
	@Transactional
	public ClientDTO update(Long id, ClientDTO entityDTO) {
		try {
			Client entity = repository.getOne(id);
			entity.setName(entityDTO.getName());
			entity.setCpf(entityDTO.getCpf());
			entity.setIncome(entityDTO.getIncome());
			entity.setBirthDate(entityDTO.getBirthDate());
			entity.setChildren(entityDTO.getChildren());
			repository.save(entity);
			return new ClientDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ServiceEntityNotFoundException("Id " + id +" not found ");
		}
		
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ServiceEntityNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity vilation");
		}
	}
}
