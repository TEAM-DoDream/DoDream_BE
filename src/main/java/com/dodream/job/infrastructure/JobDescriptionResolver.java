package com.dodream.job.infrastructure;

import com.dodream.core.infrastructure.converter.XmlToJsonConverter;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.infrastructure.caller.JobApiCaller;
import com.dodream.job.infrastructure.mapper.JobDescriptionMapper;
import com.dodream.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobDescriptionResolver {

    private final JobRepository jobRepository;
    private final JobApiCaller jobApiCaller;
    private final JobDescriptionMapper jobDescriptionMapper;
    private final XmlToJsonConverter xmlToJsonConverter;

    public String resolveJobSummary(String jobTitle){
        String jobCode = jobRepository.findByJobName(jobTitle)
                .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException)
                .getJobCode();

        String json = xmlToJsonConverter.convertXmlToJson(jobApiCaller.jobDesriptionApiCaller(jobCode));

        return jobDescriptionMapper.toJobDescriptionDto(json).jobSum();
    }
}
