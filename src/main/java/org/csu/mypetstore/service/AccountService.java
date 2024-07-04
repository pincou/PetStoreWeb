package org.csu.mypetstore.service;

import org.csu.mypetstore.entity.Account;
import org.csu.mypetstore.entity.SignOn;
import org.csu.mypetstore.vo.SignVO;

public interface AccountService {
    public SignVO getSignON(String username,String password);

    public SignVO updateAccount(Account account);

    public SignVO updateAccountPassword(SignOn signOn);

    public SignVO InsertAccount(Account account);

    public SignVO InsertSignOn(SignOn signOn);
}
