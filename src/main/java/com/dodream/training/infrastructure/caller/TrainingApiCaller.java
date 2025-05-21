package com.dodream.training.infrastructure.caller;

import com.dodream.training.presentation.value.SortBy;

public interface TrainingApiCaller {
    String getListApi(
            String pageNum, String regionCode, String ncsCode, SortBy sortBy
    );

    String getDetailApi(
            String srchTrprId,
            String srchTrprDegr,
            String srchTorgId
    );
}
