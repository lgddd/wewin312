package com.wewin.service;

import com.wewin.entity.HomeWork;

import java.util.List;

public interface HomeWorkService {
    public void addHomeWork(HomeWork homeWork);
    public void rmHomeWork(HomeWork homeWork);
    public void editHomeWork(HomeWork homeWork);
    public List<HomeWork> queryHomeWork_publisher(String openID);
    public List<HomeWork> queryHomeWork_user(String classID);
    public HomeWork queryHomeWork(String homeworkNO);

}
