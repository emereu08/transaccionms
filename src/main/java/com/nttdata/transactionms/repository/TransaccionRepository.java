package com.nttdata.transactionms.repository;

import com.nttdata.transactionms.model.entity.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
}
