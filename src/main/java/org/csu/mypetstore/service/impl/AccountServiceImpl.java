package org.csu.mypetstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.entity.Account;
import org.csu.mypetstore.entity.SignOn;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.SignOnMapper;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.vo.SignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SignOnMapper signOnMapper;

   @Override
    public SignVO getSignON(String username,String password){
        SignVO signVO=new SignVO();
       SignOn signOn=signOnMapper.selectById(username);
        String signOnTemp=signOn.getPassword();
        boolean isSame=false;
        if (password.equals(signOnTemp)){
            isSame=true;
            Account loginAccount=accountMapper.selectById(username);
//            System.out.println(loginAccount.getZip());
            signVO.setSignOnMsg("登陆成功");
            signVO.setLoginAccount(loginAccount);
        }else{
            signVO.setLoginAccount(null);
            signVO.setSignOnMsg("登录失败");
        }
        return signVO;

    }

    @Override
    public SignVO updateAccount(Account account) {
       int result=accountMapper.updateById(account);
        return null;
    }

    @Override
    public SignVO updateAccountPassword(SignOn signOn) {
       int result=signOnMapper.updateById(signOn);
        return null;
    }

    @Override
    public SignVO InsertAccount(Account account) {
       int result=accountMapper.insert(account);
        return null;
    }

    @Override
    public SignVO InsertSignOn(SignOn signOn) {
       int result=signOnMapper.insert(signOn);
        return null;
    }
}
