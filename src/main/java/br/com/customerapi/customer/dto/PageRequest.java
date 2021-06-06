package br.com.customerapi.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class PageRequest implements Pageable {

    private static final Integer LIMITE = 10;

    private int page;
    private int size;
    private int limit;
    private String orderBy;
    private String orderDirection;

    public PageRequest() {
        this.page = 0;
        this.size = LIMITE;
        this.limit = LIMITE;
        this.orderBy = "id";
        this.orderDirection = "ASC";
    }

    @Override
    public long getOffset() {
        return page * size;
    }

    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    public boolean isUnpaged() {
        return false;
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public Sort getSort() {
        return Sort.by(Sort.Direction.fromString(this.orderDirection), this.orderBy);
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int i) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Optional.empty();
    }
}
