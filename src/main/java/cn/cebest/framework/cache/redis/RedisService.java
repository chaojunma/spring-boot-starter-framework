package cn.cebest.framework.cache.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis相关方法
 * @author maming
 * @date 2018/3/28
 */
public interface RedisService {
	
	/**
	 * 设置key-value
	 * @param key
	 * @param val
	 * @return
	 */
	public void set(String key, String value);
	
	/**
	 * 设置key-value值并设置key的过期时间
	 * @param key
	 * @param seconds 以秒为单位
	 * @param value
	 * @return
	 */
	public void setEx(String key,  int seconds, String value);
	
	/**
	 * 通过key获取value值
	 * @param key
	 * @return
	 */
	public String get(String key);
	
	
	/**
	 * 判断缓存中是否存在key值
	 * @param key
	 * @return
	 */
	public boolean existKey(String key);
	
	
	/**
	 * 删除key值
	 * @param key
	 * @return
	 */
	public void del(String key);
	
	
	/**
	 * 获取key值的自增长数据
	 * @param key
	 * @return
	 */
	public long incr(String key);
	
	
	/**
	 * 添加map数据
	 * @param key   键值
	 * @param filed 字段
	 * @param value 字段值
	 * @return
	 */
	public void hset(String key, String field, String value);
	
	
	/**
	 * 获取map中的字段值
	 * @param key   键值
	 * @param field 字段
	 * @return
	 */
	public String hget(String key, String field);
	
	
	/**
	 * 设置map数据
	 * @param key 键值
	 * @param map map对象
	 * @return
	 */
	public void hmset(String key, Map<String,String> map);
	
	
	/**
	 * 获取map数据
	 * @param key    键值
	 * @param fields 多个字段
	 * @return
	 */
	public List<Object> hmget(String key, Collection<Object> fields);
	
	
	/**
	 * 获取map中字段的长度
	 * @param key
	 * @return
	 */
	public long hlen(String key);
	
	
	/**
	 * 获取map对象中的所有字段
	 * @param key
	 * @return
	 */
	public Set<Object> hkeys(String key);
	
	
	/**
	 * 获取map对象中的所有value值
	 * @param key
	 * @return
	 */
	public List<Object> hvals(String key);
	
	
	/**
	 * 向list中push数据
	 * @param key
	 * @param values
	 * @return
	 */
	public long lpush(String key, String value);
	
	
	/**
	 * 从list中取数据
	 * @param key
	 * @return
	 */
	public String rpop(String key);
	
	
	/**
	 * 获取list长度
	 * @param key
	 * @return
	 */
	public long llen(String key);
	
	
	/**
	 * 获取list数据
	 * @param key
	 * @param start 起始位置
	 * @param end   结束位置 -1代表取得所有
	 * @return
	 */
	List<String> lrange(String key, long start, long end);
	
	
	/**
	 * 向set中添加数据
	 * @param key
	 * @param members
	 * @return
	 */
	public long sadd(String key, String ...members);
	
	
	/**
	 * 返回set集合长度
	 * @param key
	 * @return
	 */
	public long scard(String key);
	
	
	/**
	 * 获取set集合的所有元素
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key);
	
	
	/**
	 * 判断member是否是集合中的元素
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key, String member);
	
	
	/**
	 * 移除set集合中的元素
	 * @param key
	 * @param members
	 * @return
	 */
	public long srem(String key, Object ...members);
	
	
	/**
	 * 返回set集合中一个随机元素
	 * @param key
	 * @return
	 */
	public String srandmember(String key);
	
}
