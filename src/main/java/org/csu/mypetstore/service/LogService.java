package org.csu.mypetstore.service;

import org.csu.mypetstore.entity.LogSheet;

import java.util.List;

public interface LogService {
    public void addRecord(String username, String operation);

    public List<LogSheet> searchRecord(String username);
}
