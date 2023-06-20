package cn.org.opendfl.sharding.user.po;




import cn.org.opendfl.sharding.config.annotations.ShardingKey;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 
 * @author kelvin
 * @since  2019
 *
 */

@Table(name = "t_user")
public class UserPo implements Serializable {
	static{
		AnnotationUtils.initShardkingKey(UserPo.class);
	}

    /**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	private Long id;

	/**
	 * userId
	 */
	@ShardingKey(shardTableCount = 4, tablePrefix = "t_user_")
	private Long userId;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 年龄
	 */
	private Integer age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}