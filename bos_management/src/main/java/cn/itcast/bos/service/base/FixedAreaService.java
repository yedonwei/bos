package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FixedAreaService {
    /**
     * 添加
     * @param FixedArea
     */
    public void save(FixedArea FixedArea);

    /**
     * 分页
     * @param pageable
     * @return
     */
    Page<FixedArea> findPageData(Pageable pageable);

}
