package com.dodream.training.util.executer;

public interface TrainingApiExecuter {
    String callListApi(
            String pageNum, String regionCode, String ncsCode
    );

    String callDetailApi(
            String trprId, String trprDegr, String torgId
    );
}
