package cn.org.opendfl.shardings.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_dict")
public class Dict {

    @Id
    private Long id;

    //    @Id
    private Date createTime;

    private String dictType;


}
