package com.dodream.training.util.executer;

import java.time.LocalDate;

public interface TrainingApiExecuter {
    String callListApi(
            String pageNum, String regionCode, String ncsCode, LocalDate startDate, LocalDate endDate
    );

    String callDetailApi(
            String trprId, String trprDegr, String torgId
    );
}
