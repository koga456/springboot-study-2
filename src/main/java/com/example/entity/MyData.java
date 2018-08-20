package com.example.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.validation.Phone;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="mydata")
@Setter
@Getter
@NamedQueries (
    @NamedQuery(
        name="findWithName",
        query="from MyData where name like :fname"
    )
)
public class MyData {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  @NotNull
  private long id;
  
  @Column(length = 50, nullable = false)
  @NotEmpty(message="�󔒂͕s��")
  private String name;
  
  @Column(length = 200, nullable = true)
  @Email(message="���[���A�h���X�̂�")
  private String mail;
  
  @Column(nullable = true)
  @Min(value=0, message="�[���ȏ�")
  @Max(value=200, message="200�ȉ�")
  private Integer age;
  
  @Column(nullable = true)
  @Phone(onlyNumber=true)
  private String memo;
  
  @OneToMany(cascade=CascadeType.ALL)
  @Column(nullable = true)
  private List<MsgData> msgdates;
}
