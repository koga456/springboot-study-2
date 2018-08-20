package com.example.controller;


import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.baen.MyDataBean;
import com.example.dao.MyDataDaoImpl;
import com.example.entity.MyData;
import com.example.repository.MyDataRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HelloController {

  private final MyDataRepository myDataRepository;
  
  private final EntityManager entityManager;
  
  private final MyDataBean myDataBean;
  
  MyDataDaoImpl dao;
  
  @PostConstruct
  public void init() {
    dao = new MyDataDaoImpl(entityManager);
    MyData d1 = new MyData();
    d1.setName("tuyano");
    d1.setAge(123);
    d1.setMail("syoda@tuyano.com");
    d1.setMemo("090999999");
    myDataRepository.saveAndFlush(d1);
    
    MyData d2 = new MyData();
    d2.setName("hanako");
    d2.setAge(15);
    d2.setMail("hanako@flower");
    d2.setMemo("080888888");
    myDataRepository.saveAndFlush(d2);
    
    MyData d3 = new MyData();
    d3.setName("sachiko");
    d3.setAge(37);
    d3.setMail("sachiko@happy");
    d3.setMemo("070777777");
    myDataRepository.saveAndFlush(d3);
  }
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index(
      @ModelAttribute("formModel") MyData mydata,
      ModelAndView mav) {
    mav.setViewName("index");
    mav.addObject("msg", "this is sample content.");
    mav.addObject("formModel", mydata);
    Iterable<MyData> list = myDataRepository.findAll();
    mav.addObject("datalist", list);
    return mav;
  }
  
  @RequestMapping(value = "/", method = RequestMethod.POST)
  @Transactional(readOnly=false)
  public ModelAndView form(
      @ModelAttribute("formModel") @Validated MyData mydata,
      BindingResult result,
      ModelAndView mav) {
    ModelAndView res = null;
    if (!result.hasErrors()) {
      myDataRepository.saveAndFlush(mydata);
      res = new ModelAndView("redirect:/");
    } else {
      mav.setViewName("index");
      mav.addObject("msg", "sorry, error is occured...");
      Iterable<MyData> list = myDataRepository.findAll();
      mav.addObject("datalist", list);
      res = mav;
    }
    return res;
    
  }
  
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public ModelAndView edit(
      @ModelAttribute MyData mydata,
      @PathVariable int id,
      ModelAndView mav) {
    mav.setViewName("edit");
    mav.addObject("title", "edit mydata.");
    Optional<MyData> data = myDataRepository.findById((long)id);
    mav.addObject("formModel", data.get());
    return mav;
  }
  
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  @Transactional(readOnly=false)
  public ModelAndView update(
      @ModelAttribute MyData mydata,
      ModelAndView mav) {
    myDataRepository.saveAndFlush(mydata);
    return new ModelAndView("redirect:/");
  }
  
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public ModelAndView delete(
      @PathVariable int id,
      ModelAndView mav) {
    mav.setViewName("delete");
    mav.addObject("title", "delete mydata.");
    Optional<MyData> data = myDataRepository.findById((long)id);
    mav.addObject("formModel", data.get());
    return mav;
  }
  
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @Transactional(readOnly=false)
  public ModelAndView delete(
      @RequestParam long id,
      ModelAndView mav) {
    myDataRepository.deleteById(id);
    return new ModelAndView("redirect:/");
  }
  
  @RequestMapping(value = "/find", method = RequestMethod.GET)
  public ModelAndView find(ModelAndView mav) {
    mav.setViewName("find");
    mav.addObject("title", "Find Page");
    mav.addObject("msg", "MyDataのサンプルです。");
    mav.addObject("value", "");
    Iterable<MyData> list = dao.getAll();
    mav.addObject("datalist", list);
    return mav;
  }
  
  @RequestMapping(value = "/find", method = RequestMethod.POST)
  public ModelAndView serach(
      HttpServletRequest request,
      ModelAndView mav) {
    mav.setViewName("find");
    String param = request.getParameter("fstr");
    if (param == "") {
      mav = new ModelAndView("redirect:/find");
    } else {
      mav.addObject("title", "Find Result");
      mav.addObject("msg", "「" + param + "」の検索結果");
      mav.addObject("value", param);
      Iterable<MyData> list = dao.find(param);
      mav.addObject("datalist", list);
    }
    return mav;
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ModelAndView indexById(
      @PathVariable long id,
      ModelAndView mav) {
    mav.setViewName("pickup");
    mav.addObject("title", "Pickup Page");
    String table = "<table>"
        + myDataBean.getTableTagById(id)
        + "</table>";
    mav.addObject("msg", "pickup data id = " + id);
    mav.addObject("data", table);
    return mav;
  }
  
  @RequestMapping(value = "/page", method = RequestMethod.GET)
  public ModelAndView index(
      ModelAndView mav,
      Pageable pageable) {
    mav.setViewName("index2");
    mav.addObject("title", "Find Page");
    mav.addObject("msg", "MyDataのサンプルです。");
    Page<MyData> list = myDataRepository.findAll(pageable);
    mav.addObject("datalist", list);
    return mav;
  }
}
