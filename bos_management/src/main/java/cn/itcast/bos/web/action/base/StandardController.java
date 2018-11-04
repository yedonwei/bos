package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StandardController {

	// 注入Service对象
	@Autowired
	private StandardService standardService;
	
	// 添加操作
	@RequestMapping("standard_save")
	public String save(Standard standard1) {
		System.out.println("添加收派标准....");
		standardService.save(standard1);
		return "redirect:/pages/base/standard.html";
	}
	
	
	// 查询所有收派标准方法
    @RequestMapping("standard_findAll")
    @ResponseBody
	public List<Standard>  findAll() {
		List<Standard> list = standardService.findAll();
		return list;
	}

	@RequestMapping("standard_pageQuery")
	@ResponseBody
	public Map<String, Object>  pageQuery(Integer page,Integer rows) {
		//从前端页面床过来的分页数据
		System.out.println("page:"+page+"rows:"+rows);
		Pageable pageable=new PageRequest(page-1,rows);
		Page<Standard> pageData = standardService.findPageData(pageable);
		//返回数据,创建map集合
		Map<String,Object> map= new HashMap<>();
		map.put("total",pageData.getTotalElements());
		map.put("rows",pageData.getContent());
		return map;
	}
}
