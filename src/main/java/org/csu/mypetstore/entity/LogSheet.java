package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("logsheet")
public class LogSheet {
    @TableId(value = "userId")
    private String username;
    @TableField("record")
    private String record;
    @TableField("recordDate")
    private Date recordDate;
}
