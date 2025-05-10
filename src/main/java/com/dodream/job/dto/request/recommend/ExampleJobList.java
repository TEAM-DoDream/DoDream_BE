package com.dodream.job.dto.request.recommend;

import com.dodream.job.domain.Job;

import java.util.List;

public record ExampleJobList(

        String jobName,

        String requiresCertification,

        String workTimeSlot,

        String salaryType,

        String salaryCost,

        String interpersonalContactLevel,

        String physicalActivityLevel,

        String emotionalLaborLevel,

        List<String> certificationNames,

        List<String> certificationPreparationPeriod
) {

    public static ExampleJobList from(Job job){
        return new ExampleJobList(
                job.getJobName(),
                job.getRequiresCertification().getDescription(),
                job.getWorkTimeSlot().getDescription(),
                job.getSalaryType().getDescription(),
                String.valueOf(job.getSalaryCost()),
                job.getInterpersonalContactLevel().getDescription(),
                job.getPhysicalActivityLevel().getDescription(),
                job.getEmotionalLaborLevel().getDescription(),
                job.getCertificationNames(job.getCertifications()),
                job.getCertificationPeriods(job.getCertifications())
        );
    }

    @Override
    public String toString() {
        return String.join(",",
                jobName,
                requiresCertification,
                workTimeSlot,
                salaryType,
                salaryCost,
                interpersonalContactLevel,
                physicalActivityLevel,
                emotionalLaborLevel,
                String.join("|", certificationNames),
                String.join("|", certificationPreparationPeriod)
        );
    }
}
