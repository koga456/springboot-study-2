package com.example.controller;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.MsgDataDaoImpl;
import com.example.dao.MyDataDaoImpl;
import com.example.entity.MsgData;
import com.example.repository.MsgDataRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MsgDataController {

  private final MsgDataRepository msgDataRepository;
  
  private final EntityManager entityManager;
  
  MsgDataDaoImpl msgDatadao;
  
  MyDataDaoImpl dao;
  
  @PostConstruct
  public void init() {
    System.out.println("ok");
    msgDatadao = new MsgDataDaoImpl(entityManager);
  }
  
  @RequestMapping(value = "/msg", method = RequestMethod.GET)
  public ModelAndView msg(ModelAndView mav) {
    mav.setViewName("showMsgData");
    mav.addObject("title", "Sample");
    mav.addObject("msg", "MsdDataのサンプルです。");
    MsgData msgData = new MsgData();
    mav.addObject("formModel", msgData);
    Iterable<MsgData> list = msgDatadao.getAll();
    mav.addObject("datalist", list);
    return mav;
  }
  
  @RequestMapping(value = "/msg", method = RequestMethod.POST)
  @Transactional(readOnly=false)
  public ModelAndView msgform(
      @ModelAttribute("formModel") @Valid MsgData msgdata,
      Errors result,
      ModelAndView mav) {
    if (result.hasErrors()) {
      mav.setViewName("showMsgData");
      mav.addObject("title", "Sample [ERROR]");
      mav.addObject("msg", "値を再チェックして下さい！");
      return mav;
    } else {
      msgDataRepository.saveAndFlush(msgdata);
      return new ModelAndView("redirect:/msg");
    }
    
  }
}
