package com.kadoozin.mscartoes.database.repository;

import com.kadoozin.mscartoes.database.model.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Integer> {
    @Query("""
            select cc
            from ClienteCartao cc
            where replace(replace(replace(cc.cpf, '.', ''), '-', ''), ' ', '') = :cpfNormalizado
            """)
    List<ClienteCartao> findByCpfNormalizado(@Param("cpfNormalizado") String cpfNormalizado);
}
