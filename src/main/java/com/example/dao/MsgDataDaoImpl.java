package com.example.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.entity.MsgData;

@Repository
public class MsgDataDaoImpl implements MsgDataDao<MsgData> {
  
  private static final long serialVersionUID = 1L;
  
  private EntityManager entityManager;

  public MsgDataDaoImpl() {
    super();
  }
  
  public MsgDataDaoImpl(EntityManager entityManager) {
    this();
    this.entityManager = entityManager;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<MsgData> getAll() {
    return entityManager.createQuery("from MsgData").getResultList();
  }
  
  @Override
  public MsgData findById(long id) {
    return (MsgData)entityManager.createQuery("from MsgData where id = " + id).getSingleResult();
  }
  
}
