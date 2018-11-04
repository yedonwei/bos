package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface AreaService {

    public void save(Area area);

    Page<Area> findPageData(Pageable pageable);

    List<Area> save(ArrayList<Area> list);
}
