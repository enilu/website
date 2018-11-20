package cn.enilu.website.blog.service;

import cn.enilu.website.blog.cache.TimeCacheMap;
import cn.enilu.website.blog.dao.TagRepository;
import cn.enilu.website.blog.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  on  2018/7/23 0023
 * TagService
 *
 * @author enilu
 */
@Service
public class TagService {
    public static TimeCacheMap<String, Object> cacheTag = new TimeCacheMap<String, Object>(3600, 2);
    public static  final String HOST_TAG = "hot_tag";
    public static  final String ALL_TAG = "all_tag";
    @Autowired
    private TagRepository tagRepository;
    public List<Tag> findAll(){
        List<Tag> list = (List<Tag>) cacheTag.get(ALL_TAG);
        if(list==null) {
          list = tagRepository.findAllByOrderByTagAsc();
          cacheTag.put(ALL_TAG,list);
        }
        return list;
    }
    public List<Tag> findHotTag(){
        List<Tag> list = (List<Tag>) cacheTag.get(HOST_TAG);
        if(list==null) {
            List idList = tagRepository.findHotTag();
            List<Long> idList2 = new ArrayList<Long>(20);
            for (int i = 0; i < idList.size(); i++) {
                idList2.add(((BigInteger) idList.get(i)).longValue());
            }
            list = tagRepository.findAllByIdIn(idList2);
            cacheTag.put(HOST_TAG,list);
        }
        return list;
    }
}
