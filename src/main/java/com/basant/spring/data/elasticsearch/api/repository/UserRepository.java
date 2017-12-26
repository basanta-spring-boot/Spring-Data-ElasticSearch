package com.basant.spring.data.elasticsearch.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.basant.spring.data.elasticsearch.api.model.User;

public interface UserRepository extends ElasticsearchRepository<User, Long> {

	List<User> findByName(String name);

	List<User> findByProfession(String profession);

	Page<User> findBySalary(long salary, Pageable pageable);

}
