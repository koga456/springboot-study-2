package com.example.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.MyData;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {

  public Optional<MyData> findById(long id);
  
  public Page<MyData> findAll(Pageable pageable);

}
