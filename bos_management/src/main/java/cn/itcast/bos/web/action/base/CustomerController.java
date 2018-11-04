package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.crm.domain.Customer;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Controller
public class CustomerController {

    @RequestMapping("fixedArea_findHasAssociationFixedAreaCustomers")
    @ResponseBody
    public Collection findHasAssociationFixedAreaCustomers(FixedArea fixedArea) {
        // 使用webClient调用 webService接口
        Collection<? extends Customer> collection=null;
        if(fixedArea!=null&& StringUtils.isNotBlank(String.valueOf(fixedArea.getId()))){
            collection = WebClient.create("http://localhost:9002/crm_management/services/customweService/associationfixedareacustomers/" + fixedArea.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .getCollection(Customer.class);
        }
        return collection;
    }
}
