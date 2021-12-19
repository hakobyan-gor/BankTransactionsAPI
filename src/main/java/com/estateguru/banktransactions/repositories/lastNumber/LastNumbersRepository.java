package com.estateguru.banktransactions.repositories.lastNumber;

import com.estateguru.banktransactions.models.entities.lastNumber.LastNumbers;
import com.estateguru.banktransactions.repositories.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastNumbersRepository extends CommonRepository<LastNumbers> {
}
