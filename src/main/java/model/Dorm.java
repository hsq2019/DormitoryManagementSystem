package model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 寝室
 */
@Getter
@Setter
@ToString
public class Dorm {
    
    private Integer id;

    /**
     * 房号
     */
    private String dormNo;

    /**
     * 描述
     */
    private String dormDesc;

    /**
     * 宿舍楼id
     */
    private Integer buildingId;

    /**
     * 创建时间
     */
    private Date createTime;
}