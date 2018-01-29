/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.busqueda.model;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author Edward
 */
public class ConexionRedis {

    // Threadsafe pool of network connections
    JedisPool pool;
    // Redis connection
    Jedis jedis;

    public Jedis getDirectConnection() {
        jedis = new Jedis("localhost");
        return jedis;
    }

    public void closeDirectConnection() {
        if (jedis != null) {
            jedis.close();
        }
    }

    public Jedis getConnection() {
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
        jedis = pool.getResource();
        return jedis;
    }

    public void destroyPool() {
        if (jedis != null) {
            jedis.close();
        }
        if (pool != null) {
            pool.destroy();
        }
    }
}
