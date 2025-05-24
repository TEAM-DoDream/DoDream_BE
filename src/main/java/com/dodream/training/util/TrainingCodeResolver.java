package com.dodream.training.util;

import com.dodream.job.domain.Job;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.ncs.exception.NcsErrorCode;
import com.dodream.ncs.repository.NcsRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingCodeResolver {

    private final RegionRepository regionRepository;
    private final JobRepository jobRepository;
    private final NcsRepository ncsRepository;

    public String resolveRegionCode(String regionName){
        if(regionName == null || regionName.isEmpty()) return null;

        Region region = regionRepository.findByRegionName(regionName)
                .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);
        return region.getRegionCode();
    }

    public String resolveNcsCode(String jobName) {
        if (jobName == null || jobName.isEmpty()) return null;

        Job job = jobRepository.findByJobName(jobName)
                .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        return ncsRepository.findByNcsName(job.getNcsName())
                .orElseThrow(NcsErrorCode.NOT_FOUND_NCS::toException)
                .getNcsCode();
    }
}
