package com.wewin.service.Impl;

import com.wewin.entity.Notice;
import com.wewin.service.NoticeService;
import com.wewin.util.JSONResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {


    @Override
    @Transactional
    public JSONResult addNotice(Notice notice) {


        return null;
    }
}
