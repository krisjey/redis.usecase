/******************************************************
 * author : Kris Jeong
 * published : 2013. 6. 18.
 * project name : redis-book
 *
 * Email : smufu@naver.com, smufu1@hotmail.com
 *
 * Develop JDK Ver. 1.6.0_13
 *
 * Issue list.
 *
 * 	function.
 *     1.
 *
 ********** process *********
 *
 ********** edited **********
 *
 ******************************************************/
package io.redis.usecase.java;

import java.util.*;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

import redis.clients.jedis.*;
import redis.clients.util.Hashing;

/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * Create a Sharded jedis pool.
 */
public class ShardedJedisHelper {
//	protected static final String SHARD1_HOST = "192.168.56.102";
	protected static final String SHARD1_HOST = "localhost";
	protected static final int SHARD1_PORT = 6380;

//	protected static final String SHARD2_HOST = "192.168.56.102";
	protected static final String SHARD2_HOST = "localhost";
	protected static final int SHARD2_PORT = 6381;

	private final Set<ShardedJedis> connectionList = new HashSet<ShardedJedis>();

	private final ShardedJedisPool shardedPool;

	/**
	 * Constructor of Jedis helper. It cannot be called from out of class.
	 */
	private static class LazyHolder {
		@SuppressWarnings("synthetic-access")
		private static final ShardedJedisHelper INSTANCE = new ShardedJedisHelper();
	}

	/**
	 * Constructor of sharded jedis pool.
	 * It cannot be called from out of class.
	 */
	private ShardedJedisHelper() {
		Config config = new Config();
		config.maxActive = 20;
		config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(SHARD1_HOST, SHARD1_PORT));
		shards.add(new JedisShardInfo(SHARD2_HOST, SHARD2_PORT));

		this.shardedPool = new ShardedJedisPool(config, shards, Hashing.MURMUR_HASH);
	}

	/**
     * Get the sharded jedisHelper instance from holder
     * @return JedisHelper instance
     */
	@SuppressWarnings("synthetic-access")
	public static ShardedJedisHelper getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * Get the jedis client connection form Sharded jedis pool.
	 * @return sharded jedis instance.
	 */
	final public ShardedJedis getConnection() {
		ShardedJedis jedis = this.shardedPool.getResource();
		this.connectionList.add(jedis);

		return jedis;
	}

	/**
     * Return jedis instance to Jedis pool
     * @param used jedis instance
     */
	final public void returnResource(ShardedJedis jedis) {
		this.shardedPool.returnResource(jedis);
	}

	/**
     * destory jedis pool
     */
	final public void destoryPool() {
		Iterator<ShardedJedis> jedisList = this.connectionList.iterator();
		while (jedisList.hasNext()) {
			ShardedJedis jedis = jedisList.next();
			this.shardedPool.returnResource(jedis);
		}

		this.shardedPool.destroy();
	}
}
