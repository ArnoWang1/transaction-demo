package com.demo.service.user.dao;

import com.demo.service.user.model.MainTransaction;
import org.springframework.data.repository.CrudRepository;

public interface MainTransactionDAO extends CrudRepository<MainTransaction, Long> {
}
