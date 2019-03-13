package com.infogrupo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infogrupo.cursomc.entity.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
