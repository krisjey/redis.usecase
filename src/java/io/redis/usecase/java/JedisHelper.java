package io.redis.usecase.java;
import java.util.*;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import redis.clients.jedis.*;

public class JedisHelper {
//	protected static final String REDIS_HOST = "192.168.56.101";
	protected static final String REDIS_HOST = "localhost";
	protected static final int REDIS_PORT = 6379;
	private final Set<Jedis> connectionList = new HashSet<Jedis>();
	private final JedisPool pool;

	/**
	 * Constructor of Jedis helper. It cannot be called from out of class.
	 */
	private JedisHelper() {
		Config config = new Config();
		config.maxActive = 20;
		config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

		this.pool = new JedisPool(config, REDIS_HOST, REDIS_PORT, 5000);
	}

	/**
	 * Singleton holder
	 */
	private static class LazyHolder {
		@SuppressWarnings("synthetic-access")
		private static final JedisHelper INSTANCE = new JedisHelper();
	}

	/**
	 * Get the jedisHelper instance from holder
	 * @return JedisHelper instance
	 */
	@SuppressWarnings("synthetic-access")
	public static JedisHelper getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * Get jedis instance from Jedis pool
	 * @return jedis
	 */
	final public Jedis getConnection() {
		Jedis jedis = this.pool.getResource();
		this.connectionList.add(jedis);

		return jedis;
	}

	/**
	 * Return jedis instance to Jedis pool
	 * @param used jedis instance
	 */
	final public void returnResource(Jedis jedis)	{
		this.pool.returnResource(jedis);
	}

	/**
	 * destory jedis pool
	 */
	final public void destoryPool() {
		Iterator<Jedis> jedisList = this.connectionList.iterator();
		while (jedisList.hasNext()) {
			Jedis jedis = jedisList.next();
			this.pool.returnResource(jedis);
		}

		this.pool.destroy();
	}
}
