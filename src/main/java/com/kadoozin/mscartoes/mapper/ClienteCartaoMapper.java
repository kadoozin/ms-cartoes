package com.kadoozin.mscartoes.mapper;

import com.kadoozin.mscartoes.database.model.ClienteCartao;
import com.kadoozin.mscartoes.dto.response.ClienteCartaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteCartaoMapper {

    @Mapping(source = "cartao.nome", target = "nome")
    @Mapping(source = "cartao.bandeiraCartao", target = "bandeira")
    @Mapping(source = "limite", target = "limite")
    ClienteCartaoResponse toResponse(ClienteCartao clienteCartao);
}
