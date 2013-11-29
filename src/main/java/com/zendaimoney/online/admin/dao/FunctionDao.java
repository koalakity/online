package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.Function;

public interface FunctionDao extends PagingAndSortingRepository<Function, Long>{

	Iterable<Function> findByFunctionCodeLikeOrderByIdAsc(String functionCode);

}
