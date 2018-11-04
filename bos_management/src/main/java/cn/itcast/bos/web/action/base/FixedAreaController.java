package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.crm.domain.Customer;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FixedAreaController {

    @Autowired
    private FixedAreaService fixedAreaService;

    @RequestMapping("fixedArea_save")
    public String save(FixedArea fixedArea) {
        System.out.println("添加定区....");
        fixedAreaService.save(fixedArea);
        return "redirect:./pages/base/fixed_area.html";
    }

    @RequestMapping("fixedArea_pageQuery")
    @ResponseBody
    public Map<String, Object> pageQuery(Integer page, Integer rows) {
        //从前端页面床过来的分页数据
        System.out.println("page:" + page + "rows:" + rows);
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> pageData = fixedAreaService.findPageData(pageable);
        //返回数据,创建map集合
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageData.getTotalElements());
        map.put("rows", pageData.getContent());
        return map;
    }

    /**
     * 查询未关联定区的客户
     *
     * @param
     * @return
     */
    @RequestMapping("fixedArea_findNoAssociationCustomers")
    @ResponseBody
    public Collection findNoAssociationCustomers() {
        // 使用webClient调用 webService接口
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:9002/crm_management/services/customweService/noassociationcustomers")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        return collection;
    }

    @RequestMapping("/fixedArea_associationCustomersToFixedArea")
    public String associationCustomersToFixedArea(String[] customerIds, FixedArea fixedArea) {
        String customerIdStr = StringUtils.join(customerIds, ",");  //1,2
        WebClient.create("http://localhost:9002/crm_management/services/customweService/associationcustomerstofixedarea?customerIdStr=" + customerIdStr + "&fixedAreaId=" + fixedArea.getId())
                .post(null);//触发服务方 方法上的@POST注解
        return "redirect:/pages/base/fixed_area.html";
    }

}