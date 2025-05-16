package com.dodream.training.infrastructure.caller;

public interface TrainingApiCaller {
    String getListApi(
            String pageNum, String regionCode, String ncsCode,
            String startDate, String endDate
    );

    String getDetailApi(
            String srchTrprId,
            String srchTrprDegr,
            String srchTorgId
    );
}
