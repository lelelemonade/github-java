package org.carrot.githubjava.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhonglishen
 */
@Data
@AllArgsConstructor
public class TopRepo {

    @ExcelProperty("仓库名称")
    String repoName;
    @ExcelProperty("标星数")
    int stars;
    @ExcelProperty("地址")
    String address;
    @ExcelProperty("描述")
    String description;
}
