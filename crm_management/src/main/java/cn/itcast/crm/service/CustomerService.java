package cn.itcast.crm.service;

import cn.itcast.crm.domain.Customer;

import javax.ws.rs.*;
import java.util.List;

public interface CustomerService {

	/**
	 * 查询所有未关联客户列表
	 */
	@Path("/noassociationcustomers")
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Customer> findNoAssociationCustomers();
//
////	/**
////	 * 已经关联到指定定区的客户列表
////	 */
////	@Path("/associationfixedareacustomers/{id}")
////	@GET
////	@Produces({"application/xml", "application/json"})
////	public List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("id") String id);
//
	/**
	 * 将客户关联到定区上，将所有客户id 拼成字符串 1,2,3
	 */
	@Path("/associationcustomerstofixedarea")
	@POST
	public void associationCustomersToFixedArea(@QueryParam("customerIdStr") String customerIdStr,@QueryParam("fixedAreaId") String fixedAreaId);

//	/**
//	 * 根据电话号码查找一个客户
//	 */
//	@Path("/customer/telephone/{telephone}")
//	@GET
//	@Consumes({"application/xml", "application/json"})
//	public Customer findByTelephone(@PathParam("telephone") String telephone);
//
//	/**
//	 * 登陆
//	 */
//	@Path("customer/login")
//	@GET
//	@Consumes({"application/xml", "application/json"})
//	public Customer login(@QueryParam("telephone") String telephone, @QueryParam("password") String password);



	/**
	 * 已经关联到指定定区的客户列表
	 */     //associationfixedareacustomers/dq001
	@Path("/associationfixedareacustomers/{fixedareaid}")
	@GET
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	public List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("fixedareaid") String fixedAreaId);


}