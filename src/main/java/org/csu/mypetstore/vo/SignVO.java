package org.csu.mypetstore.vo;

import lombok.Data;
import org.csu.mypetstore.entity.Account;

@Data
public class SignVO {
    private Account loginAccount;
    private String signOnMsg;
}
