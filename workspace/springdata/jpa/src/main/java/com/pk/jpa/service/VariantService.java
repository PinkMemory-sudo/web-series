package com.pk.jpa.service;

import com.pk.jpa.entity.VariantFeedbackEntity;
import com.pk.jpa.model.PageResultData;
import com.pk.jpa.model.param.FeedbackQueryParam;

public interface VariantService {

    /**
     * 反馈列表分页查询
     */
    PageResultData<VariantFeedbackEntity> findPage(FeedbackQueryParam param, int pageNumber, int pageSize);
}
