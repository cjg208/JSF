package com.jsf.service.user;

import com.github.pagehelper.PageInfo;
import com.jsf.database.mapper.UserMapper;
import com.jsf.database.model.User;
import com.jsf.database.model.custom.IdText;
import com.jsf.database.model.excel.UserExcel;
import com.jsf.utils.string.IdGen;
import com.jsf.utils.string.StringUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户Service
 * Created by xujunfei on 2016/12/21.
 */
@Service
@Transactional
public class UserService {

    @Resource
    private UserMapper userMapper;
    /*@Resource
    private UidGenerator uidGenerator;*/

    /**
     * 按id查询用户
     *
     * @param id
     * @return
     */
    @Cacheable(value = "rds", key = "'findUserById'+#id")
    public User findUserById(Long id) {
        return userMapper.findById(id);
    }


    /**
     * 用户更新信息
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "rds", key = "'findUserById'+#user.id")
    public int updateUser(User user) {
        return userMapper.update(user);
    }

    /**
     * 用户分页查询
     *
     * @param condition
     * @return
     */
    public PageInfo findUserByPage(User condition) {
        // 需要自定义排序的，必须指定默认排序
        if (StringUtil.isBlank(condition.getPageSort())) {
            condition.setPageSort("u.id DESC");
        }
        condition.setPage(true);
        List<User> list = userMapper.findByCondition(condition);
        return new PageInfo(list);
    }

    /**
     * @param condition
     * @return
     */
    public List<User> findByCondition(User condition) {
        return userMapper.findByCondition(condition);
    }

    /**
     * 通过id查询字段
     * <p>只能在service或controller调用，禁止直接在request传参</p>
     *
     * @param userId
     * @param field
     * @return
     */
    public Object findFieleByUserId(Long userId, String field) {
        return userMapper.findFieleByUserId(userId, field);
    }

    /**
     * 手机号模糊查询
     *
     * @param phone
     * @return
     */
    public List<IdText> findUserLikePhone(String phone) {
        return userMapper.findUserLikePhone(phone);
    }

    /**
     * 按手机号查询
     *
     * @param phone
     * @return
     */
    public User findUserByPhone(String phone) {
        User condition = new User();
        condition.setPhone(phone);
        List<User> list = userMapper.findByCondition(condition);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 按手机号查询总数
     *
     * @param phone
     * @return
     */
    public int findUserCountByPhone(String phone) {
        return userMapper.findCountByKey("phone", phone);
    }

    /**
     * 按邮箱查询总数
     *
     * @param email
     * @return
     */
    public int findUserCountByEmail(String email) {
        return userMapper.findCountByKey("email", email);
    }

    /**
     * 账号密码查询
     *
     * @param account
     * @param password
     * @return
     */
    public User findUserByNameAndPwd(String account, String password) {
        return userMapper.findByNameAndPwd(account, password);
    }

    /**
     * 导出Excel
     *
     * @param condition
     * @return
     */
    public List<UserExcel> findUserExcelByCondition(User condition) {
        condition.setPageSort("u.id DESC");
        return userMapper.findForExcel(condition);
    }

    /**
     * 新增用户
     *
     * @return
     */
    public int insertUser(User user) {
        user.setId(IdGen.get().nextId());
        // 新增用户
        return userMapper.insert(user);
    }

    /**
     * 删除用户(逻辑删除)
     *
     * @param userId
     * @return
     */
    public int deleteUser(Long userId) {
        return userMapper.delete(userId);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public int deleteBatch(Long[] ids) {
        return userMapper.deleteBatch(ids);
    }

}
