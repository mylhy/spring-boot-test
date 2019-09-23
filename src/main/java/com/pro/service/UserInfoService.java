package com.pro.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pro.dao.SysRoleDao;
import com.pro.dao.UserInfoDao;
import com.pro.entity.PageInfo;
import com.pro.entity.SysRole;
import com.pro.entity.UserInfo;

@Service
public class UserInfoService{
    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private SysRoleDao sysRoleDao;
    
    /**
     * 查询所有用户
     */
    public List<UserInfo> findAll(){
    	return userInfoDao.findAll();
    }
    
    /**
     * 根据条件查询用户
     * @param query 条件
     * @return
     */
	public Page<UserInfo> findAll(Map<String, String> queryMap,PageInfo pageInfo) {
//		Pageable pageable=PageRequest.of(0, 20);
		//jpa分页是从0页开始
		Pageable pageable = PageRequest.of(pageInfo.getPageNum()-1, pageInfo.getPageSize(), Sort.by(Direction.DESC, "createDate"));
        Specification<UserInfo> example = new Specification<UserInfo>() {
			@Override
			public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 //定义集合来确定Predicate[] 的长度，因为CriteriaBuilder的or方法需要传入的是断言数组
                List<Predicate> predicates = new ArrayList<>();
                if(queryMap.get("query_username")!=null) {
                    Predicate predicate = cb.like(root.get("username"), "%"+queryMap.get("query_username")+"%");
                    predicates.add(predicate);
                }
                
              //判断结合中是否有数据
                if (predicates.size() == 0) {
                    return null;
                }
              //将集合转化为CriteriaBuilder所需要的Predicate[]
                Predicate[] predicateArr = new Predicate[predicates.size()];
                predicateArr = predicates.toArray(predicateArr);

             // 返回所有获取的条件： 条件 or 条件 or 条件 or 条件
                return cb.or(predicateArr);
			}};
			Page<UserInfo> list= userInfoDao.findAll(example, pageable);
			return list;
	}
    /**
     * 根据条件查询，分页
     */
    public Page<UserInfo> findAllPage(String nickName){
    	//规格定义
        Specification<UserInfo> specification = new Specification<UserInfo>() {
            /**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(StringUtils.isNotBlank(nickName)){ //添加断言
                    Predicate likeNickName = cb.like(root.get("nickName"),nickName+"%");
                    predicates.add(likeNickName);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
//        Sort sort = new Sort(Sort.Direction.DESC,"createTime"); //创建时间降序排序
        //分页信息
//        Pageable pageable = new PageRequest(0,20); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return userInfoDao.findAll(specification,PageRequest.of(0, 20));
    }
    
    public UserInfo findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }
    
    /**
     * 保存用户信息
     */
    public void saveUserInfo(UserInfo userInfo) {
    	userInfoDao.save(userInfo);
    }

	public UserInfo register(String username, String password) {
		UserInfo userInfo=new UserInfo();
		userInfo.setName("用户_"+System.currentTimeMillis());
		userInfo.setSalt(new SecureRandomNumberGenerator().nextBytes().toString());
		userInfo.setUsername(username);
		userInfo.setPassword(new SimpleHash("md5",password,userInfo.getCredentialsSalt(),2).toString());
		
		userInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
		
		userInfoDao.save(userInfo);
		return userInfo;
	}

	public void delete(Integer id) {
		userInfoDao.deleteById(id);
	}

	public UserInfo findById(Integer uid) {
		return userInfoDao.getOne(uid);
	}
	
	/**
	 * 保存用户角色信息
	 */
	public UserInfo setRoles(Integer uid,Integer[] rids) {
		//查user
		UserInfo user=this.findById(uid);
		if(user==null) {
			return null;
		}
		List<SysRole> rList=new ArrayList<SysRole>();
		if(rids!=null && rids.length>0) {

			for(Integer rid : rids){  
	            SysRole role = sysRoleDao.getOne(rid);
	            rList.add(role);
	        }  
		}
		user.setRoleList(rList);
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		this.saveUserInfo(user);
		return user;
	}
	/**
	 * 判断是否已存在账号，
	 * @param username 账号
	 * @return false 不存在，true 存在
	 */
	public boolean isRepeatUsername(String username) {
		UserInfo user=userInfoDao.findByUsername(username);
		return user==null?false:true;
	}
	/**
	 * 添加用户
	 * @param name 登录账号
	 * @param password	登录密码
	 * @param user	操作人
	 */
	public UserInfo addUser(String username, String password, UserInfo user) {
		UserInfo userInfo=new UserInfo();
		userInfo.setName("用户_"+System.currentTimeMillis());
		userInfo.setSalt(new SecureRandomNumberGenerator().nextBytes().toString());
		userInfo.setUsername(username);
		userInfo.setPassword(new SimpleHash("md5",password,userInfo.getCredentialsSalt(),2).toString());
		userInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
		userInfo.setCreateUser(user.getUid());
		
		userInfoDao.save(userInfo);
		return userInfo;
	}
}