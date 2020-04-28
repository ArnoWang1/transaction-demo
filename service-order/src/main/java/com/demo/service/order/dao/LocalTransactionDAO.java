package com.demo.service.order.dao;

import com.demo.service.order.model.LocalTransaction;
import org.springframework.data.repository.CrudRepository;

public interface LocalTransactionDAO extends CrudRepository<LocalTransaction, Long> {
}
