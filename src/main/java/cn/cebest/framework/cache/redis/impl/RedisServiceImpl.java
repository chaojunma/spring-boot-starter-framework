package cn.cebest.framework.cache.redis.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import cn.cebest.framework.cache.redis.RedisService;



/**
 * Redis相关方法
 * @author maming
 * @date 2018/3/28
 */
public class RedisServiceImpl implements RedisService{
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	

	/**
	 * 设置key-value
	 * @param key
	 * @param val
	 * @return
	 */
	@Override
	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}
	
	
	/**
	 * 设置key-value值并设置key的过期时间
	 * @param key
	 * @param seconds 以秒为单位
	 * @param value
	 * @return
	 */
	@Override
	public void setEx(String key, int seconds, String value) {
		stringRedisTemplate.opsForValue().set(key, value, seconds);
	}

	
	/**
	 * 通过key获取value值
	 * @param key
	 * @return
	 */
	@Override
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	
	/**
	 * 判断缓存中是否存在key值
	 * @param key
	 * @return
	 */
	@Override
	public boolean existKey(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	
	/**
	 * 删除key值
	 * @param key
	 * @return
	 */
	@Override
	public void del(String key) {
		stringRedisTemplate.delete(key);
	}

	
	/**
	 * 获取key值的自增长数据
	 * @param key
	 * @return
	 */
	@Override
	public long incr(String key) {
		return stringRedisTemplate.boundValueOps(key).increment(1);
	}

	
	/**
	 * 添加map数据
	 * @param key   键值
	 * @param filed 字段
	 * @param value 字段值
	 * @return
	 */
	@Override
	public void hset(String key, String field, String value) {
		stringRedisTemplate.opsForHash().put(key, field, value);
	}

	
	/**
	 * 获取map中的字段值
	 * @param key   键值
	 * @param field 字段
	 * @return
	 */
	@Override
	public String hget(String key, String field) {
		return (String) stringRedisTemplate.opsForHash().get(key, field);
	}

	
	/**
	 * 设置map数据
	 * @param key 键值
	 * @param map map对象
	 * @return
	 */
	@Override
	public void hmset(String key, Map<String, String> map) {
		stringRedisTemplate.opsForHash().putAll(key, map);
	}

	
	/**
	 * 获取map数据
	 * @param key    键值
	 * @param fields 多个字段
	 * @return
	 */
	@Override
	public List<Object> hmget(String key, Collection<Object> fields) {
		return stringRedisTemplate.opsForHash().multiGet(key, fields);
	}

	
	/**
	 * 获取map中字段的长度
	 * @param key
	 * @return
	 */
	@Override
	public long hlen(String key) {
		return stringRedisTemplate.opsForHash().size(key);
	}

	
	/**
	 * 获取map对象中的所有字段
	 * @param key
	 * @return
	 */
	@Override
	public Set<Object> hkeys(String key) {
		return stringRedisTemplate.opsForHash().keys(key);
	}

	
	/**
	 * 获取map对象中的所有value值
	 * @param key
	 * @return
	 */
	@Override
	public List<Object> hvals(String key) {
		return stringRedisTemplate.opsForHash().values(key);
	}

	
	/**
	 * 向list中push数据
	 * @param key
	 * @param values
	 * @return
	 */
	@Override
	public long lpush(String key, String value) {
		return stringRedisTemplate.opsForList().leftPush(key, value);
	}

	
	/**
	 * 从list中取数据
	 * @param key
	 * @return
	 */
	@Override
	public String rpop(String key) {
		return stringRedisTemplate.opsForList().rightPop(key);
	}

	
	/**
	 * 获取list长度
	 * @param key
	 * @return
	 */
	@Override
	public long llen(String key) {
		return stringRedisTemplate.opsForList().size(key);
	}

	
	/**
	 * 获取list数据
	 * @param key
	 * @param start 起始位置
	 * @param end   结束位置 -1代表取得所有
	 * @return
	 */
	@Override
	public List<String> lrange(String key, long start, long end) {
		return stringRedisTemplate.opsForList().range(key, start, end);
	}

	
	/**
	 * 向set中添加数据
	 * @param key
	 * @param members
	 * @return
	 */
	@Override
	public long sadd(String key, String... members) {
		return stringRedisTemplate.opsForSet().add(key, members);
	}

	
	/**
	 * 返回set集合长度
	 * @param key
	 * @return
	 */
	@Override
	public long scard(String key) {
		return stringRedisTemplate.opsForSet().size(key);
	}

	
	/**
	 * 获取set集合的所有元素
	 * @param key
	 * @return
	 */
	@Override
	public Set<String> smembers(String key) {
		return stringRedisTemplate.opsForSet().members(key);
	}

	
	/**
	 * 判断member是否是集合中的元素
	 * @param key
	 * @param member
	 * @return
	 */
	@Override
	public boolean sismember(String key, String member) {
		return stringRedisTemplate.opsForSet().isMember(key, member);
	}

	
	/**
	 * 移除set集合中的元素
	 * @param key
	 * @param members
	 * @return
	 */
	@Override
	public long srem(String key, Object... members) {
		return stringRedisTemplate.opsForSet().remove(key, members);
	}

	
	/**
	 * 返回set集合中一个随机元素
	 * @param key
	 * @return
	 */
	@Override
	public String srandmember(String key) {
		return stringRedisTemplate.opsForSet().randomMember(key);
	}
	

}
