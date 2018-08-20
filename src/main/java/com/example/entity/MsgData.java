package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="msgdata")
@Setter
@Getter
public class MsgData {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  @NotNull
  private long id;
  
  @Column
  private String title;
  
  @Column(nullable = false)
  @NotEmpty
  private String message;
  
  @ManyToOne
  private MyData mydata;
  
  public MsgData() {
    super();
    mydata = new MyData();
  }
}
