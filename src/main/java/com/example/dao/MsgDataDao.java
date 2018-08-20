package com.example.dao;

import java.io.Serializable;
import java.util.List;

import com.example.entity.MsgData;

public interface MsgDataDao <T> extends Serializable {
  
  public List<MsgData> getAll();
  
  public MsgData findById(long id);

}
