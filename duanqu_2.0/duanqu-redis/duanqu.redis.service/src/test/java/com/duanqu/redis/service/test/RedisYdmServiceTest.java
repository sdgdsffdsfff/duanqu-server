package com.duanqu.redis.service.test;

import java.io.Console;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.TunnelRefusedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisCollection;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.model.ActionModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.model.ActionModel.Action;
import com.duanqu.common.vo.ActionForm;
import com.duanqu.common.vo.MessageForm;
import com.duanqu.redis.service.hot.RedisHotServiceImpl;
import com.duanqu.redis.utils.key.JMSKeyManager;
import com.duanqu.redis.utils.key.SearchKeyManager;
import com.duanqu.redis.utils.key.SystemKeyManager;
import com.duanqu.redis.utils.key.TimelineKeyManager;
import com.duanqu.redis.utils.key.UserKeyManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis-test-applicationContext.xml")
@SuppressWarnings("unchecked")
public class RedisYdmServiceTest {

	Log logger = LogFactory.getLog(RedisYdmServiceTest.class);

	@Resource(name = "relationRedisTemplate")
	private RedisTemplate relationTemplate;
	@Resource(name = "userRedisTemplate")
	private RedisTemplate userTemplate;

	@Resource(name = "jmsRedisTemplate")
	private RedisTemplate jmsTemplate;

	@Resource
	private RedisTemplate messageRedisTemplate;
	private final HashMapper<UserModel, String, String> userMapper = new DecoratingStringHashMapper<UserModel>(
			new JacksonHashMapper<UserModel>(UserModel.class));

	// @Test
	public void testRedis() {
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong("kv:user:id",
				relationTemplate.getConnectionFactory());
		// long c=redisAtomicLong.decrementAndGet();//decr
		// long b=redisAtomicLong.addAndGet(2);//incrby
		// redisAtomicLong.set(2);//set 命令
		// long c=redisAtomicLong.incrementAndGet();//incr
		// System.out.println(redisAtomicLong.doubleValue());
		// System.out.println(redisAtomicLong.floatValue());
		// System.out.println(redisAtomicLong.getKey());
		/*
		 * long a=redisAtomicLong.getAndAdd(5);// long
		 * b=redisAtomicLong.getAndSet(10);getset long
		 * c=redisAtomicLong.getAndDecrement(); long
		 * d=redisAtomicLong.getAndIncrement();
		 */

		/*
		 * String str="hm:user:{0}:data"; String
		 * strnewString=MessageFormat.format(str,2);
		 */
		UserModel userModel = new UserModel();
		userModel.setUid(redisAtomicLong.incrementAndGet());
		userModel.setNickName("ydm");
		userModel.setEmail("ydm305365@163.com");
		userModel.setCreateTime(System.currentTimeMillis());
		// 根据这个键获取一个hash的操作对象，可以操作这个键的对象，操作Hash
		BoundHashOperations<String, String, Object> hopts = userTemplate
				.boundHashOps(UserKeyManager.getUserInfoKey(userModel.getUid()));
		// 操作list
		// userTemplate.boundListOps("");

		// String
		// userTemplate.boundValueOps("");
		// Set
		// userTemplate.boundSetOps("");
		// ZSet
		// userTemplate.boundZSetOps("");
		DefaultRedisMap map = new DefaultRedisMap<String, String>(
				UserKeyManager.getUserInfoKey(Long.parseLong("34")),
				userTemplate);

		hopts.putAll(userMapper.toHash(userModel));// 添加对象
		Object object = hopts.get("uid");
		// hopts.delete("createTime");
		// Map map2= hopts.entries();

		if (StringUtils.hasText(userModel.getEmail())) {
			userTemplate.boundValueOps(
					UserKeyManager.getUserIdByEmailKey(userModel.getEmail()))
					.set(String.valueOf(userModel.getUid()));
		}
		userTemplate.boundListOps(UserKeyManager.getUserByTimeKey()).leftPush(
				String.valueOf(userModel.getUid()));
		// userTemplate.boundListOps(UserKeyManager.getUserByTimeKey()).rightPush(String.valueOf(userModel.getUid()));
		List<String> list = userTemplate.boundListOps(
				UserKeyManager.getUserByTimeKey()).range(0, -1);

		List<UserModel> listUserList = new ArrayList<UserModel>();
		/*
		 * for(String uid:list){ UserModel userModel2=userMapper.fromHash(new
		 * DefaultRedisMap<String,
		 * String>(UserKeyManager.getUserInfoKey(Long.parseLong(uid)),userTemplate));
		 * listUserList.add(userModel2); }
		 */

		Set set = userTemplate.keys("hm:user:*:data");
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			listUserList.add(userMapper
					.fromHash(new DefaultRedisMap<String, String>(iterator
							.next(), userTemplate)));
		}

		System.out.println(listUserList.size());

		// userTemplate.boundListOps(UserKeyManager.getUserByTimeKey()).leftPop();
		// userTemplate.boundListOps(UserKeyManager.getUserByTimeKey()).rightPop();
		// userTemplate.b
		/*
		 * userTemplate.execute(new RedisCallback<Object>(){
		 * 
		 * @Override public Object doInRedis(RedisConnection conncection) throws
		 * DataAccessException{
		 * 
		 * byte
		 * key[]=userTemplate.getStringSerializer().serialize("hm:user:1:data");
		 * 
		 * BoundHashOperations<Serializable, byte[], byte[]>
		 * hopts=userTemplate.boundHashOps(key);
		 * hopts.put(userTemplate.getStringSerializer().serialize("username"),
		 * userTemplate.getStringSerializer().serialize("ydm"));
		 * hopts.put(userTemplate.getStringSerializer().serialize("password"),
		 * userTemplate.getStringSerializer().serialize("305365"));
		 * conncection.hMSet(key, hopts.entries()); return null; }
		 * 
		 * });
		 */

		// long a=redisAtomicLong.get();//get 命令
	}

	// Redis String 类型的学习与测试
	// @Test
	@SuppressWarnings("unused")
	public void testRedis_String() {
		//
		RedisAtomicLong ra = new RedisAtomicLong("kv:user:id", relationTemplate
				.getConnectionFactory());

		long a = ra.addAndGet(2);// 先增加2在获取增加后的值
		byte b = ra.byteValue();
		long c = ra.decrementAndGet();// 先减一在获取减后的值
		double d = ra.doubleValue();
		float e = ra.floatValue();
		long f = ra.get();
		long g = ra.getAndAdd(2);// 先获取在加2
		long h = ra.getAndDecrement();// 先获取在减1
		long i = ra.getAndIncrement();// 先获取在加1
		long j = ra.getAndSet(4);// 先获取并从新设置新值
		String k = ra.getKey();// 获取当前操作的key的名字
		DataType dType = ra.getType();
		long l = ra.incrementAndGet();// 先增加在获取增加后的值
		ra.set(5);// 设置新值
		boolean boo = ra.persist();// 查看key是不是过期
		ra.rename("kv:user:id:new");// 从新命名键
		ra.rename("kv:user:id");
		ra.set(10);

		BoundValueOperations<String, Object> bvOs = relationTemplate
				.boundValueOps(UserKeyManager.getUserIdByNickName("ydm"));

		int m = bvOs.append("haha");// 向key中添加指定值，并返回添加值的字符串长度
		Object object = bvOs.getAndSet("dm.yang");// 获取原有值并重新设置新值
		Object object2 = bvOs.get();
		Object object3 = bvOs.get(0, 2);// 包括2
		long size = bvOs.size();// 获取value的长度
		RedisOperations<String, Object> rOperations = bvOs.getOperations();

		long n = bvOs.getExpire();
		bvOs.set("new");
		String s1 = (String) bvOs.get();
		bvOs.set("today", 2);// 从第个位置开始用新值代替
		String s2 = (String) bvOs.get();
		TimeUnit timeUnit = TimeUnit.NANOSECONDS;
		TimeUnit timeUnit2 = TimeUnit.DAYS;
		// bvOs.set("tuesday", 20, TimeUnit.MILLISECONDS);
		bvOs.expire(60, TimeUnit.MINUTES);// 设置过期时间，60分钟
		long x = bvOs.getExpire();// 获取过期剩余时间
		// ValueOperations 和
		// BoundValueOperations的区别，本人自己理解BoundValueOperations事先绑定key，后续操作无需在指定key
		// ValueOperations 操作需要对应的key
		ValueOperations<String, Object> vOperations = relationTemplate
				.opsForValue();

		UserModel userModel = new UserModel();
		userModel.setUid(ra.incrementAndGet());
		userModel.setNickName("ydm");
		userModel.setEmail("ydm305365@163.com");
		userModel.setCreateTime(System.currentTimeMillis());
		// 测试有问题，String类型可不可以保存对象，在网上说是可以保存任意的值，我这里测试保存对象出错??
		bvOs.set(userModel);
		Object o = bvOs.get();

	}

	// List 类型的学习与测试
	// @Test
	@SuppressWarnings("unused")
	public void testRedis_List() {
		RedisAtomicLong ra = new RedisAtomicLong("kv:user:id", relationTemplate
				.getConnectionFactory());
		BoundListOperations<String, Object> blOs = userTemplate
				.boundListOps(UserKeyManager.getUserByTimeKey());
		Object a = blOs.index(2);// 获取对应索引的值
		Object b = blOs.leftPop();
		Object c = blOs.leftPop(1, TimeUnit.MINUTES);// ??
		Object d = blOs.range(0, -1);
		long m = blOs.leftPush(String.valueOf(ra.incrementAndGet()));
		blOs.leftPush(String.valueOf(ra.decrementAndGet()), String.valueOf(ra
				.incrementAndGet()));// ??
		blOs.remove(2, "28");// 移除?
		Object e = blOs.rightPop();
		Object f = blOs.rightPop(1, TimeUnit.MINUTES);
		long n = blOs.rightPush(String.valueOf(ra.incrementAndGet()));
		blOs.rightPush("2", "27");
		long x = blOs.rightPushIfPresent("17");// ??也是向里添加数据，具体用处不明白
	}

	// Set 类型的学习与测试,db4
	// @Test
	@SuppressWarnings("unused")
	public void testRedis_Set() {
		BoundSetOperations<String, Object> bsOs = jmsTemplate
				.boundSetOps(SystemKeyManager.getBadwordsKey());
		bsOs.add(String.valueOf(2));// ??参数是object类型，为什么我放int类型缺报错
		bsOs.add(String.valueOf(3));
		bsOs.add(String.valueOf(5));
		SetOperations<String, Object> sOperations = jmsTemplate.opsForSet();

		sOperations.add("set:badwords:a", "2");
		sOperations.add("set:badwords:a", "4");
		sOperations.add("set:badwords:a", "3");

		sOperations.add("set:badwords:b", "2");
		sOperations.add("set:badwords:b", "4");

		sOperations.add("set:badwords:c", "2");
		sOperations.add("set:badwords:c", "4");
		sOperations.add("set:badwords:c", "5");
		sOperations.remove("set:badwords:c", "5");

		List<String> list = new ArrayList<String>();
		list.add("set:badwords:a");
		list.add("set:badwords:b");
		list.add("set:badwords:c");

		Set<Object> set = bsOs.diff(list);// 获取集合的差集，list为key的集合
		Iterator<Object> iterator = set.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		Set<Object> set2 = bsOs.diff("set:badwords:d");
		Iterator<Object> iterator2 = set2.iterator();
		while (iterator2.hasNext()) {
			System.out.println(iterator2.next());
		}

		bsOs.diffAndStore(list, "set:badwords:e");// 把集合的交集放到另外一个key里
		// inter 交集
		Set<Object> set3 = bsOs.intersect(list);
		// 并集
		Set<Object> set4 = bsOs.union(list);

		bsOs.intersectAndStore(list, "set:badwords:f");

		boolean isMember = bsOs.isMember("2");// 判断2是否是该成员,如果是返回true

		Set<Object> set5 = bsOs.members();// 获取所有成员

		Object object = bsOs.randomMember();// 随机获取成员

		Object object2 = bsOs.pop();// 随机弹出一个成员

		Set<Object> set6 = bsOs.members();

	}

	// Hashes类型的测试与学习
	// @Test
	@SuppressWarnings("unused")
	public void testRedis_Hashes() {
		RedisAtomicLong ra = new RedisAtomicLong("kv:user:id", relationTemplate
				.getConnectionFactory());
		UserModel userModel = new UserModel();
		userModel.setUid(ra.incrementAndGet());
		userModel.setNickName("ydm");
		userModel.setEmail("ydm305365@163.com");
		userModel.setCreateTime(System.currentTimeMillis());
		BoundHashOperations<String, String, Object> bhOs = userTemplate
				.boundHashOps(UserKeyManager.getUserInfoKey(userModel.getUid()));

		bhOs.put("uid", String.valueOf(userModel.getUid()));
		bhOs.put("nickName", userModel.getNickName());

		Map<String, Object> map = bhOs.entries();// 获取该键对应所有键值对

		bhOs.delete("nickName");// 删除该键对应的nickName键值对
		Object object = bhOs.get("uid");
		boolean isHas = bhOs.hasKey("uid");// 判断该键是否存在
		bhOs.put("nickName", userModel.getNickName());
		bhOs.put("email", userModel.getEmail());
		Set<String> set = bhOs.keys();// 返回k集合
		long uid = bhOs.increment("uid", 3);// 把一键对应value加上3并返回加后的值
		List<Object> list = bhOs.values();// 返回value集合
		// bhOs.multiGet(keys);
		long size = bhOs.size();// 获取k的数量

		UserModel userModel2 = new UserModel();
		userModel2.setUid(ra.incrementAndGet());
		userModel2.setNickName("ydm2");
		userModel2.setEmail("ydm3053652@163.com");
		userModel2.setCreateTime(System.currentTimeMillis());

		bhOs.putAll(userMapper.toHash(userModel2));

	}

	// SortedSets 测试与学习
	// @Test
	@SuppressWarnings("unused")
	public void testRedis_SortedSets() {
		BoundZSetOperations<String, Object> bzOs = relationTemplate
				.boundZSetOps(TimelineKeyManager.getUserPublicList(1));
		bzOs.add("1", System.currentTimeMillis());
		bzOs.add("5", System.currentTimeMillis());
		bzOs.add("3", System.currentTimeMillis());

		bzOs.add("1", 3);// 如果添加相同的分数不同，将替换分数
		bzOs.add("5", 2);
		bzOs.add("3", 1);
		bzOs.add("4", 4);
		bzOs.add("6", 7);
		/*
		 * bzOs.remove("1"); bzOs.remove("5");移除对应元素 bzOs.remove("3");
		 */
		// 3，
		long count = bzOs.count(1, 2);// 获取，1,2之间的元素数量，1,2是分数
		double score = bzOs.incrementScore("5", 45);// 将5的分数加上45
		Set<Object> set = bzOs.rangeByScore(1, 7);// 获取分数1,7之间的值
		Set<Object> set2 = bzOs.range(3, 5);// 按索引位置获取之间的值
		long rank = bzOs.rank("3");// ?
		Set<Object> set3 = bzOs.reverseRange(1, 7);// 按相反方向

	}

	// @Test
	@SuppressWarnings("unused")
	public void testRedis_Key() {
		/*
		 * Set<String> set=userTemplate.keys("hm:user:*:data"); List<UserModel>
		 * list=new ArrayList<UserModel>(); Iterator<String>
		 * iterator=set.iterator(); while(iterator.hasNext()){
		 * list.add(userMapper.fromHash(new DefaultRedisMap<String,
		 * String>(iterator.next(),userTemplate))); } NoticeMessage
		 * noticeMessage=new
		 * NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.LOGIN);
		 * 
		 * for(UserModel um:list){ Object object=JSON.toJSONString(um);
		 * jmsTemplate.boundListOps(JMSKeyManager.getLoginListKey()).leftPush(JSON.toJSONString(um));
		 * jmsTemplate.convertAndSend("duanqu-notice", JSON.toJSONString(new
		 * NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.LOGIN)));
		 * jmsTemplate.convertAndSend("duanqu-notice-ydm", "22222");
		 * jmsTemplate.convertAndSend("duanqu-notice-ydm", um); }
		 */

		Set<String> set = messageRedisTemplate.keys("hm:message:*:data");

		System.out.println(set.size());

	}

	// @Test
	public void testRedis_Transaction() {
		userTemplate.multi();// 事物开始，接下所有命令被存放到队列中
		ValueOperations<String, Object> vops = userTemplate.opsForValue();
		vops.append("test", "1");
		vops.append("test2", "2");
		vops.append("test3", "3");
		vops.append("test4", "dddd");
		// vops.increment("test4", 5);
		userTemplate.exec();
	}

	// 测试登录
	// @Test
	public void testLogin() {
		Set<String> set = userTemplate.keys("kv:user:open:*:*");
		for (String str : set) {
			System.out.println(str);
			DataType dataType = userTemplate.type(str);
			System.out.println(dataType);
			BoundValueOperations<String, Object> bvos = userTemplate
					.boundValueOps(str);
			System.out.println(bvos.get());

		}

	}

	// @Test
	public void testSort() {

	}

	@Test
	public void testTimeline() {
		/*
		 * Set<String> set=relationTemplate.keys("set:user:*:public:timeline");
		 * Double stroe=0.0; for(String key:set){ BoundZSetOperations<String,
		 * Object> bzsOperations=relationTemplate.boundZSetOps(key); String
		 * array[]=key.split(":");
		 * 
		 * Set bzsValuseSet=bzsOperations.reverseRangeWithScores(0, -1);
		 * if(bzsValuseSet!=null && bzsValuseSet.size()>0){ TypedTuple obj=null;
		 * for(Iterator iterator=bzsValuseSet.iterator();iterator.hasNext();){
		 * obj=(TypedTuple)iterator.next(); String string=obj.getValue()+"";
		 * String strArray[]=string.split(":"); BoundZSetOperations<String,
		 * Object> boundZSetOperations=null;
		 * if(Action.CREATE.getMark()==Integer.parseInt(strArray[1])){
		 * boundZSetOperations=relationTemplate.boundZSetOps(TimelineKeyManager.getUserFollowsCreateTimelineKey(Long.parseLong(array[2]))); }
		 * if(Action.LIKE.getMark()==Integer.parseInt(strArray[1])||
		 * Action.FORWARD.getMark()==Integer.parseInt(strArray[1])){
		 * boundZSetOperations=relationTemplate.boundZSetOps(TimelineKeyManager.getUserFollowsForwardTimelineKey(Long.parseLong(array[2]))); }
		 * 
		 * Set boundNewSet=boundZSetOperations.reverseRangeWithScores(0, -1);
		 * for(Iterator iterator2=boundNewSet.iterator();iterator2.hasNext();){
		 * TypedTuple typedTuple=(TypedTuple)iterator2.next(); String
		 * stringNew=typedTuple.getValue()+""; if(string.equals(stringNew)){
		 * if(typedTuple.getScore()>obj.getScore()){
		 * stroe=typedTuple.getScore(); }else{ stroe=obj.getScore(); } break;
		 * }else{ stroe=obj.getScore(); } } if(boundZSetOperations!=null){
		 * boundZSetOperations.add(string, stroe); } } } }
		 */
        BoundZSetOperations<String, Object> bzSet=null;
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(
				"kv:user:global:id", userTemplate.getConnectionFactory());
		long total = redisAtomicLong.get();
		for (int i = 1; i <= total; i++) {
			try {
				String key=TimelineKeyManager
				.getPublicTimelineKey(i);
				if (relationTemplate.hasKey(key)){
					BoundZSetOperations<String, Object> boundZSetOperations=relationTemplate.boundZSetOps(key);
					Set<TypedTuple<Object>> set=boundZSetOperations.reverseRangeWithScores(0, -1);
					if(set!=null && set.size()>0){
						for(Iterator<TypedTuple<Object>> iterator=set.iterator();iterator.hasNext();){
							TypedTuple<Object> typedTuple=iterator.next();
							ActionModel actionModel=ActionModel.parse(typedTuple.getValue()+"");
							if(actionModel!=null){
								if(Action.CREATE.getMark()==actionModel.getAction()){
									bzSet=relationTemplate.boundZSetOps(TimelineKeyManager.getUserFollowsCreateTimelineKey(i));
								}else if(Action.LIKE.getMark()==actionModel.getAction() || Action.FORWARD.getMark()==actionModel.getAction()){
									bzSet=relationTemplate.boundZSetOps(TimelineKeyManager.getUserFollowsForwardTimelineKey(i));
								}
								if(bzSet!=null){
								Long index=bzSet.rank(typedTuple.getValue());
								if(index!=null){
								Set<TypedTuple<Object>> setNewSet=	bzSet.reverseRangeWithScores(index,index);
								if(setNewSet!=null && setNewSet.size()>0){
								for(Iterator<TypedTuple<Object>> iterator2=setNewSet.iterator();iterator2.hasNext();){
									TypedTuple<Object> objTuple=iterator2.next();
									if(typedTuple.getScore()>objTuple.getScore()){
										bzSet.add(actionModel.toString(),typedTuple.getScore());
									}else{
										bzSet.add(actionModel.toString(),objTuple.getScore());
									}
								}
								}
								}else{
									bzSet.add(actionModel.toString(), typedTuple.getScore());
								}
								}
							}
						}	
					}
				}
			} catch (Exception e) {
				logger.error("UID:"+i+"转换过程发生了异常："+e.getMessage());
			}
			
		}
	}
}
