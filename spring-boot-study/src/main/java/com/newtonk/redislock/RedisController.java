package com.newtonk.redislock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/11/9 0009
 */
@Component
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource(name="redisTemplate")
    private ValueOperations<String, String> stringValueOperations;

    private final String lockKey = "lock";
    private String uuid = UUID.randomUUID().toString();
    /**
     * 简易版获取锁
     * @param lockSeconds 锁住的时间
     *                    存在问题：当事务者因网络或IO等执行因素导致实际执行时间超过预期锁住时间，因此需要在释放锁时抛出异常用于事务回滚
     */
    public boolean lock(int lockSeconds){
        long nowTime = System.currentTimeMillis();
        long expireTime = nowTime  + lockSeconds*1000 +1000 ;
        //setNX  -- set not exists
        if(stringValueOperations.setIfAbsent(lockKey,uuid)){
            stringRedisTemplate.expire(lockKey,lockSeconds, TimeUnit.SECONDS);
            return true;
        }else{
            return false;
        }
    }


    public void terminatedLock(){
        Thread.interrupted();
    }

    /**
     * 轮询获取锁
     * @param lockSeconds  加锁时间 秒
     * @param tryIntervalMillis 轮询时间间隔
     * @param maxTryCount   最大重试次数
     * @return
     */
    public boolean tryLoclk(final int lockSeconds, final long tryIntervalMillis, final int maxTryCount){
        return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                int tryCount = 0;
                while (true){
                    if( ++tryCount == maxTryCount){
                        return false;//超时
                    }
                    if(lock(lockSeconds)){
                        return true;
                    }
                    try {
                        Thread.sleep(tryIntervalMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }

                }
            }
        });
    }

    public boolean releaseLock(final String requestId){
        /* redis 事务实现 */
        return stringRedisTemplate.execute(new SessionCallback<Boolean>() {
            /**
             * Executes all the given operations inside the same session.
             *
             * @param operations Redis operations
             * @return return value
             */
            @Override
            public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                String value = (String) operations.opsForValue().get(lockKey);
                if(requestId.equals(value)){
                    operations.delete((K) lockKey);
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    public void main(){
        System.out.println("获取锁: "+lock(10));
        System.out.println("释放锁" + releaseLock(uuid));
    }
}
