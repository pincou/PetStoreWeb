package org.csu.mypetstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.entity.LogSheet;
import org.csu.mypetstore.persistence.LogSheetMapper;
import org.csu.mypetstore.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {
    @Autowired
    private LogSheetMapper logSheetMapper;
    @Override
    public void addRecord(String username, String operation) {
        LogSheet logSheet=new LogSheet();
        logSheet.setUsername(username);
        logSheet.setRecord(operation);
        logSheet.setRecordDate(new Date());
        logSheetMapper.insert(logSheet);
    }

    @Override
    public List<LogSheet> searchRecord(String username) {
        QueryWrapper<LogSheet> queryWrapper = new QueryWrapper<>();
        // 设置查询条件：username等于指定的值
        queryWrapper.eq("userId", username);
        // 调用selectList方法查询符合条件的记录并返回
        return logSheetMapper.selectList(queryWrapper);
    }
}
