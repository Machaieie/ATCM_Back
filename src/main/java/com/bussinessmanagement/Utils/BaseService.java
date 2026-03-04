package com.bussinessmanagement.Utils;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<RQ, RS> {
    void create(RQ dto);
    Page<RS> getAll(Pageable pageable);
    void update(RQ dto, int id);
    void remove(int id);
    RS getById(int id);
}

