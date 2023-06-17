package cn.org.opendfl.shardings.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_order")
public class Order {

	@Id
    private Long id;

//    @Id
    private Long userId;
    
    private String name;
    
    
}
