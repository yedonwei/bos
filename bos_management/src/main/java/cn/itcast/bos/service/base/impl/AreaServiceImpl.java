package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AreaServiceImpl  implements AreaService{

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void save(Area area) {
        String province = area.getProvince(); //获得省
        String city = area.getCity();  //获得市
        String district = area.getDistrict();  //获得区
        province = province.substring(0, province.length() - 1);//把省去掉
        city = city.substring(0, city.length() - 1);//把市去掉
        district= district.substring(0,district.length()-1);//把区去掉
        String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
        StringBuffer stringBuffer= new StringBuffer();
        for (String s: headByString){
            stringBuffer.append(s);
        }
        area.setPcd(stringBuffer.toString());
        //城市拼音
        String s = PinYin4jUtils.hanziToPinyin(city,"");
        area.setPinyinCity(s);
        areaRepository.save(area);
    }

    @Override
    public Page<Area> findPageData(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

    @Override
    public List<Area> save(ArrayList<Area> list) {
        return areaRepository.save(list);
    }
}
