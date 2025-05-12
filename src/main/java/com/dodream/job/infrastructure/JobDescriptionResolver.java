package com.dodream.job.infrastructure;

import com.dodream.core.infrastructure.converter.XmlToJsonConverter;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.infrastructure.caller.JobApiCaller;
import com.dodream.job.infrastructure.mapper.JobDescriptionMapper;
import com.dodream.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class JobDescriptionResolver {

    private final JobRepository jobRepository;
    private final JobApiCaller jobApiCaller;
    private final JobDescriptionMapper jobDescriptionMapper;
    private final XmlToJsonConverter xmlToJsonConverter;

    public String resolveJobSummary(String jobName){
        String jobCode = getJobCodeByJobName(jobName);
        return jobDescriptionMapper.toJobDescriptionDto(getJsonString(jobCode)).jobSum();
    }

    public String resolveJobummaryByCode(String jobCode){
        return jobDescriptionMapper.toJobDescriptionDto(getJsonString(jobCode)).jobSum();
    }

    private String getJsonString(String keyword){
        return xmlToJsonConverter.convertXmlToJson(jobApiCaller.jobDesriptionApiCaller(keyword));
    }

    private String getJobCodeByJobName(String jobName){
        return jobRepository.findByJobName(jobName)
                .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException)
                .getJobSummary();
    }
}
