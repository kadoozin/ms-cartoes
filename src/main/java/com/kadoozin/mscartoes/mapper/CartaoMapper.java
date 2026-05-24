package com.kadoozin.mscartoes.mapper;

import com.kadoozin.mscartoes.database.model.Cartao;
import com.kadoozin.mscartoes.dto.request.CartaoRequest;
import com.kadoozin.mscartoes.dto.response.CartaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartaoMapper {
    @Mapping(source = "id", target = "cartaoId")
    CartaoResponse toResponse(Cartao cartao);

    Cartao toEntity(CartaoRequest request);
}
