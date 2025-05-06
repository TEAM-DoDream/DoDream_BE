package com.dodream.job.dto.response;

import java.util.List;

public record JobDescriptionDto(
        String jobCd,
        String jobLrclNm,
        String jobMdclNm,
        String jobSmclNm,
        String jobSum,
        String way,
        List<RelMajor> relMajorList,
        List<RelCert> relCertList,
        String sal,
        String jobSatis,
        String jobProspect,
        String jobStatus,
        String jobAbil,
        String knowldg,
        String jobEnv,
        String jobChr,
        String jobIntrst,
        String jobVals,
        String jobActvImprtncs,
        String jobActvLvls,
        List<RelJob> relJobList
) {
    public record RelMajor(
            Long majorCd,
            String majorNm
    ) {}

    public record RelCert(
            String certNm
    ) {}

    public record RelJob(
            Long jobCd,
            String jobNm
    ) {}
}
