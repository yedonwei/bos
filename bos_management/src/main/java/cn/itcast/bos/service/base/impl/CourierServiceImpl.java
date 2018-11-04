package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class CourierServiceImpl implements CourierService{
	@Autowired
	private  CourierRepository courierRepository;

	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);
	}

	
	@Override
	public Page<Courier> findPageData(Specification<Courier> spec, Pageable pageable) {
    	return null;
	}

	@Override
	public Page<Courier> findPageData(Pageable pageable) {
		return courierRepository.findAll(pageable);
	}

	//作废
	@Override
	public void delBatch(String[] idArray) {
		for(String ids:idArray){
			Integer id = Integer.parseInt(ids);
			courierRepository.updateDeltag(id);
		}
	}

	//还原
	@Override
	public void restoreBatch(String[] idArray) {
		for(String ids:idArray){
			Integer id = Integer.parseInt(ids);
			courierRepository.updateByDeltag(id);
		}
	}

	@Override
	public List<Object[]> findBygroup() {

		return courierRepository.findBygroup();
	}


}
