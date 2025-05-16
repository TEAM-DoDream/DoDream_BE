package com.dodream.ncs.dto.response;

import com.dodream.ncs.domain.Ncs;

public record NcsResponseDto(

        String ncsCode,

        String ncsName
) {

    public static NcsResponseDto from(Ncs ncs){
        return new NcsResponseDto(ncs.getNcsCode(), ncs.getNcsName());
    }
}
