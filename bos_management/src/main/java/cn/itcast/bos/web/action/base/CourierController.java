package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourierController {

	// 注入Service
	@Autowired
	private CourierService courierService;
	
	// 添加快递员方法
	@RequestMapping("courier_save")
	public String save(Courier model) {
		courierService.save(model);
		return "redirect:./pages/base/courier.html";
	}
	
	// 分页列表查询
	@RequestMapping("courier_pageQuery1")
	@ResponseBody
	public Map<String, Object>  pageQuery(Integer page,Integer rows,Courier model) {
		// 封装Pageable对象
		Pageable pageable = new PageRequest(page - 1, rows);
		
		// 封装条件查询对象 Specification
		Specification<Courier> specification = new Specification<Courier>() {
			
			// Root 用于获取属性字段，CriteriaQuery可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> list = new ArrayList<>();
				
				// 简单单表查询
				if (StringUtils.isNotBlank(model.getCourierNum())) {
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%" + model.getCompany() + "%");
					list.add(p2);
				}
				if (StringUtils.isNotBlank(model.getType())) {
					Predicate p3 = cb.like(root.get("type").as(String.class), model.getType());
					list.add(p3);
				}
				
				// 多表查询
				Join<Courier, Standard> standardJoin = root.join("standard", JoinType.INNER);
				if (model.getStandard() != null && StringUtils.isNotBlank(model.getStandard().getName())) {
					Predicate p4 = cb.like(standardJoin.get("name").as(String.class), "%" + model.getStandard().getName() + "%");
					list.add(p4);
				}
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		
		// 调用业务层 ，返回 Page
		Page<Courier> pageData = courierService.findPageData(specification, pageable);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pageData.getNumberOfElements());
		result.put("rows", pageData.getContent());

		return result;
	}
	
	// 作废快递员
	@RequestMapping("courier_delBatch")
	public String delBatch(String[] ids) {
		// 按,分隔ids
		//String[] idArray = ids.split(",");
		// 调用业务层，批量作废 restoreBatch
		//courierService.delBatch(idArray);

		courierService.delBatch(ids);
		return "redirect:./pages/base/courier.html";
	}

	//还原快递员
	@RequestMapping("courier_restoreBatch")
	public String restoreBatch(String[] ids) {
		// 按,分隔ids
		//String[] idArray = ids.split(",");
		// 调用业务层，批量作废 restoreBatch
		//courierService.restoreBatch(idArray);

		courierService.restoreBatch(ids);
		return "redirect:./pages/base/courier.html";
	}

	/**
	 * 简单排序
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("courier_pageQuery")
	@ResponseBody
	public Map<String, Object>  pageQuery(Integer page,Integer rows) {
		//从前端页面床过来的分页数据
		System.out.println("page:"+page+"rows:"+rows);
		Pageable pageable=new PageRequest(page-1,rows);
		Page<Courier> pageData = courierService.findPageData(pageable);
		//返回数据,创建map集合
		Map<String,Object> map= new HashMap<>();
		map.put("total",pageData.getTotalElements());
		map.put("rows",pageData.getContent());
		return map;
	}

	@RequestMapping("courier_doCharts")
	@ResponseBody
	public Map<String, Object> findBygroup() {
		//从数据库中查询数据
		List<Object[]> list = courierService.findBygroup();
		List<String> categories = new ArrayList<>();  //["河南分公司","上海分公"....]
		List<Long> series = new ArrayList<>();  //[50,30,.....]

		for (Object[] obj : list) {
			String category = (String) obj[0];
			categories.add(category);

			long serie = (long) obj[1];
			series.add(serie);
		}
		/*for (Object[] obj : list) {
			long serie = (long) obj[1];
			series.add(serie);
		}*/
		//把数据存到map集合中返回到页面上 {"categories":["河南分公司","上海分公"....],"series":[50,30,.....]}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("categories", categories);
		result.put("series", series);
		return  result;
	}


		@RequestMapping("courier_dopieCharts")
		@ResponseBody
		public List<Object[]> dopieCharts() {
		//从数据库中查询数据
		List<Object[]> list = courierService.findBygroup();
		return  list;
	}
}