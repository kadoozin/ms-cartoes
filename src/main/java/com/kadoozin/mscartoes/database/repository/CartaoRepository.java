package com.kadoozin.mscartoes.database.repository;

import com.kadoozin.mscartoes.database.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
    List<Cartao> findByRendaMinimaLessThanEqual(@Param("rendaCliente") BigDecimal rendaCliente);
}
