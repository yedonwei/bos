package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.itcast.crm.domain.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    /**
     * 查询定区下的所有客户
     * @param fixedAreaId
     * @return
     */
    List<Customer> findCustomersByFixedAreaId(String fixedAreaId);

    /**
     * 查询定区下未关联的所有客户
     * @return
     */
    List<Customer> findCustomersByFixedAreaIdIsNull();

    /**
     * 清除订单下的所有客户
     * @param fixedAreaId
     */
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    void clearFixedAreaId(String fixedAreaId);


    @Query("update Customer set fixedAreaId = ? where id = ?")
    @Modifying
    void updateFixedAreaId(String fixedAreaId, Integer id);
}