package com.dodream.job.dto.request.clova;

import java.util.List;

public record ClovaStudioRequest(

        List<ClovaMessage> messages,

        int maxToken
){

}
