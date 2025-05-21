package com.dodream.training.util.executer;

import com.dodream.training.presentation.value.SortBy;

public interface TrainingApiExecuter {
    String callListApi(
            String pageNum, String regionCode, String ncsCode, SortBy sortBy
    );

    String callDetailApi(
            String trprId, String trprDegr, String torgId
    );
}
