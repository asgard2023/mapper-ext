package tk.mybatis.mapper.util;

import java.util.UUID;

/**
 * 用户生成主键
 * 
 * @author root
 *
 */
public class IdGenerator {
	
	private static String MIDDLE_LINE = "-";

	/**
	 * 生成UUID
	 * 去掉中划线
	 * @return
	 */
	public static String createId() {
		return createUUID().replaceAll(MIDDLE_LINE, "");
	}
	
	/**
	 * 产生UUID
	 * @return
	 */
	public static String createUUID() {
		return UUID.randomUUID().toString();
	}
}
