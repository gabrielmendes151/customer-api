package br.com.customerapi.customer.predicate;

import com.querydsl.core.BooleanBuilder;
import org.springframework.util.ObjectUtils;

public class PredicateBase {

    protected BooleanBuilder builder =  new BooleanBuilder();

    public BooleanBuilder build() {
        return this.builder;
    }

    protected boolean isNotEmpty(Object object) {
        return !ObjectUtils.isEmpty(object);
    }
}

