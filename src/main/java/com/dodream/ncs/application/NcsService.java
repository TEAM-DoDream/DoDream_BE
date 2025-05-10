package com.dodream.ncs.application;

import com.dodream.ncs.domain.Ncs;
import com.dodream.ncs.dto.response.NcsResponseDto;
import com.dodream.ncs.exception.NcsErrorCode;
import com.dodream.ncs.repository.NcsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NcsService {

    private final NcsRepository ncsRepository;

    public List<NcsResponseDto> getAllNcs(){
        return ncsRepository.findAll().stream()
                .map(NcsResponseDto::from)
                .toList();
    }

    public NcsResponseDto getNcsByNcsName(String ncsName){
        Ncs ncs = ncsRepository.findByNcsName(ncsName)
                .orElseThrow(NcsErrorCode.NOT_FOUND_NCS::toException);

        return NcsResponseDto.from(ncs);
    }

    public NcsResponseDto getNcsByNcsCode(String ncsCode){
        Ncs ncs = ncsRepository.findByNcsCode(ncsCode)
                .orElseThrow(NcsErrorCode.NOT_FOUND_NCS::toException);

        return NcsResponseDto.from(ncs);
    }
}
