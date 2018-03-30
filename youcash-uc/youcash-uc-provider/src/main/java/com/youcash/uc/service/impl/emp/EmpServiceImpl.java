package com.youcash.uc.service.impl.emp;

import com.youcash.uc.entity.Emp;
import com.youcash.uc.entity.User;
import com.youcash.uc.mapper.EmpMapper;
import com.youcash.uc.model.Result;
import com.youcash.uc.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author linhansheng
 * @date 2018/3/21.
 */
@Service("empService")
@Transactional(rollbackFor = Exception.class)
public class EmpServiceImpl implements EmpService{
    @Autowired
    private EmpMapper empMapper;
    @Override
    public Result<Emp> queryEmp(String empNo) {
        Emp emp = empMapper.selectByPrimaryKey(new Short("7369"));
        System.out.println("aaaa");
        return Result.success(emp);
    }

    @Override
    public Result<String> pathParam(String userName) {
        return Result.success(userName);
    }

    @Override
    public Result<Emp> queryParam(String name, int empno) {
        Emp emp = new Emp();
        emp.setEname(name);
        emp.setEmpno(empno);
        return Result.success(emp);
    }

    @Override
    public Result<String> formParam(String name) {
        return Result.success(name);
    }

    @Override
    public Result<String> matrixParam(String name, Integer age) {
        return Result.success(name+":"+age);
    }

    @Override
    public Result<User> beanParam(User user) {
        return Result.success(user);
    }
}
