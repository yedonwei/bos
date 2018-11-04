package cn.itcast.crm.service.impl;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findNoAssociationCustomers() {
        return customerRepository.findCustomersByFixedAreaIdIsNull();
    }

    @Override
    public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
        // 解除关联动作（把客户表中的所有fixedAreaId置空）
        System.out.println(fixedAreaId);
        customerRepository.clearFixedAreaId(fixedAreaId);
        // 经过断点调试，发现action传递过来的customerIdStr有时是"null"
        if (StringUtils.isBlank(customerIdStr) || "null".equals(customerIdStr)) { // null  ""  "null"
            return;
        }
        // 切割字符串 1,2
        String[] customerIdArray = customerIdStr.split(",");
        for (String idStr : customerIdArray) {
            System.out.println(idStr);
            try {
                Integer id = Integer.parseInt(idStr);
                customerRepository.updateFixedAreaId(fixedAreaId, id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }

//    @Override
//    public Customer findByTelephone(String telephone) {
//        return null;
//    }
//
//    @Override
//    public Customer login(String telephone, String password) {
//        return null;
//    }

    //查询已关联到某定区的客户
    @Override
    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
        return customerRepository.findCustomersByFixedAreaId(fixedAreaId);
    }



}
