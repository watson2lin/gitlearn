package com.youcash.uc.service;

import com.youcash.uc.entity.Emp;
import com.youcash.uc.entity.User;
import com.youcash.uc.model.Result;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author
 * @date 2018/3/21.
 */
@Path("/emp")
@Produces({MediaType.APPLICATION_JSON})
public interface EmpService {
    @GET
    @Path("/queryEmp")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Result<Emp> queryEmp(String empNo);

    /**
     * http://127.0.0.1:8081/service/emp/pathParam/watson
     * @param userName
     * @return
     */
    @GET
    @Path("/pathParam/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    Result<String> pathParam(@PathParam("username") String userName);

    /**
     * http://127.0.0.1:8081/service/emp/queryParam?name=watson&no=666
     * @param name
     * @param empno
     * @return
     */
    @GET
    @Path("/queryParam")
    Result<Emp> queryParam(@QueryParam("name") String name,@QueryParam("no") int empno);

    /**
     * http://127.0.0.1:8081/service/emp/formParam
     * @param name
     * @return
     */
    @POST
    @Path("/formParam")
    @Consumes("application/x-www-form-urlencoded")
    Result<String> formParam(@FormParam("name") String name);

    /**
     * http://127.0.0.1:8081/service/emp/matrixParam
     * @param name
     * @param age
     * @return
     */
    @GET
    @Path("matrixParam")
    Result<String> matrixParam(@MatrixParam("name") String name,@MatrixParam("age") Integer age);

    /**
     * http://127.0.0.1:8081/service/emp/beanParam
     * @param user
     * @return
     */
    @GET
    @Path("/beanParam")
    Result<User> beanParam(@BeanParam User user);
}
