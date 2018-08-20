package com.example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.entity.MyData;

@Repository
public class MyDataDaoImpl implements MyDataDao<MyData> {
  
  private static final long serialVersionUID = 1L;
  
  private EntityManager entityManager;

  public MyDataDaoImpl() {
    super();
  }
  
  public MyDataDaoImpl(EntityManager entityManager) {
    this();
    this.entityManager = entityManager;
  }
  
  @Override
  public List<MyData> getAll() {
    Query query = entityManager.createQuery("from MyData");
    @SuppressWarnings("unchecked")
    List<MyData> list = query.getResultList();
    entityManager.close();
    return list;
  }
  
  @Override
  public MyData findById(long id) {
    return (MyData)entityManager.createQuery("from MyData where id = " + id).getSingleResult();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<MyData> findByName(String name) {
    return (List<MyData>)entityManager.createQuery("from MyData where name = " + name).getResultList();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<MyData> find(String fstr) {
    List<MyData> list = null;
//    String qstr = "from MyData where id = :fid or name like :fname or mail like :fmail";
//    Long fid = 0L;
//    try {
//      fid = Long.parseLong(fstr);
//    } catch(NumberFormatException e) {
//      e.printStackTrace();
//    }
    Query query = entityManager
        .createNamedQuery("findWithName")
        .setParameter("fname", "%" + fstr + "%");
    list = query.getResultList();
    return list;
  }
}
