package com.blackJack.repository;

import com.blackJack.dbo.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String> {
}
