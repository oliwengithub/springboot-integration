package com.oliwen.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * tk 测试表
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2023/6/7 22:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tk_mybatis")
public class TkMybatis {

    @Id
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;
}
