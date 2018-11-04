package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {

    /**
     * 作废
     * @param id
     */
    @Query(value = "update Courier set deltag='1' where id = ?1")
    @Modifying
    public void updateDeltag(Integer id);



    /**
     * 还原
     * @param id
     */
    @Query(value = "update Courier set deltag='0' where id = ?1")
    @Modifying
    public void updateByDeltag(Integer id);

    @Query(value = "select c.company,count(company) from Courier c group by company")
    List<Object[]> findBygroup();
}




