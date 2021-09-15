package com.pk.jpa.dao;

import com.pk.jpa.entity.VariantFeedbackEntity;
import com.pk.jpa.entity.VariantFeedbackPK;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface VariantDao extends JpaRepositoryImplementation<VariantFeedbackEntity, VariantFeedbackPK> {

}
