package cn.enilu.website.blog.service;

import cn.enilu.website.blog.cache.CollectCache;
import cn.enilu.website.blog.cache.TimeCacheMap;
import cn.enilu.website.blog.dao.NewsRepository;
import cn.enilu.website.blog.dao.NewsTagRelRepository;
import cn.enilu.website.blog.dao.TagRepository;
import cn.enilu.website.blog.entity.News;
import cn.enilu.website.blog.entity.NewsTagRel;
import cn.enilu.website.blog.entity.Tag;
import cn.enilu.website.utils.Md5Util;
import cn.enilu.website.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created  on  2018/7/18 0018
 * NewsService
 *
 * @author enilu
 */
@Service
public class NewsService {
    private Logger logger = LoggerFactory.getLogger(News.class);
    public static  final String CACHE_KEY_CATEGORY_MENU = "category_menu";
    public static TimeCacheMap<String, Object> cache = new TimeCacheMap<String, Object>(3600, 2);
    public static TimeCacheMap<String, Tag> cacheTag = new TimeCacheMap<String, Tag>(3600, 2);
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private NewsTagRelRepository newsTagRelRepository;
    @Autowired
    private CollectCache collectCache;

    public Page<News> findPage(Pageable pageRequest){
        return  findPage(null,null,pageRequest);
    }
    public Page<News> findPage(final String category,Map params, Pageable pageRequest){
        Page<News> page = newsRepository.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(StringUtil.isNotEmpty(category)){
                    list.add(criteriaBuilder.equal(root.get("category").as(String.class), category));
                }
                if(params!=null && StringUtil.isNotNullOrEmpty(params.get("author"))){
                    list.add(criteriaBuilder.equal(root.get("author").as(String.class), params.get("author").toString()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageRequest);
        return page;
    }

    public Map getCategories(){
        Map categories = (Map) cache.get(CACHE_KEY_CATEGORY_MENU);
        if(categories!=null&&!categories.isEmpty()){
           return categories;
        }
        categories = Maps.newHashMap();
        List list = newsRepository.findAllCategories();
        List<String> result = Lists.newArrayList();
        List<String> more = Lists.newArrayList();
        int count = list.size()>10?10:list.size();
        if(!list.isEmpty()){
            for(int i=0;i<count;i++){
                result.add(((Object[])list.get(i))[0].toString());

            }
            int tmp = list.size()>15?15:list.size();
            for(int i=10;i<tmp;i++){
                more.add(((Object[])list.get(i))[0].toString());
            }
        }
        categories.put("list",result);
        categories.put("more",more);
        cache.put(CACHE_KEY_CATEGORY_MENU,categories);
        return  categories;
    }
    public void save(News news){
        if(StringUtil.isEmpty(news.getUrlKey())) {
            news.setUrlKey(Md5Util.getMD5String(news.getUrl()));
        }
        news.setId(Md5Util.getMD5String(news.getAuthor()+news.getTitle()));
        collectCache.saveKey(news.getUrlKey());
        newsRepository.save(news);
        if(news.getCreateTime()==null){
            news.setCreateTime(new Date());
        }
        saveTag(news);
    }
    public void  saveTag(News news){
        String tag  = news.getTag();
        if(StringUtil.isEmpty(tag)){
            return ;
        }
        String[] tags = tag.split(",");
        for(int i=0;i<tags.length;i++){
            if(StringUtil.isEmpty(tags[i])){
                continue;
            }
            Tag tagEntity = cacheTag.get(tags[i]);
            if(tagEntity==null){
                tagEntity = new Tag();
                tagEntity.setTag(tags[i]);
                tagRepository.save(tagEntity);
                cacheTag.put(tagEntity.getTag(),tagEntity);
            }
            NewsTagRel rel = new NewsTagRel();
            rel.setIdTag(tagEntity.getId());
            rel.setIdNews(news.getId());
            newsTagRelRepository.save(rel);

        }
    }

}
