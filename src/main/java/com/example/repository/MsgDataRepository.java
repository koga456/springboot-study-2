package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.MsgData;


@Repository
public interface MsgDataRepository extends JpaRepository<MsgData, Long> {

}
