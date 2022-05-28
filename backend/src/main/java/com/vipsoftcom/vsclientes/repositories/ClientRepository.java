package com.vipsoftcom.vsclientes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vipsoftcom.vsclientes.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
}
