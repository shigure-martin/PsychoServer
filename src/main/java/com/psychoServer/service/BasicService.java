package com.psychoServer.service;

import com.psychoServer.entity.IEntity;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.psychoServer.request.OrderRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicService<T extends IEntity, ID> {
    protected PagingAndSortingRepository<T, ID> pagingAndSortingRepository;

    public BasicService() {
    }

    public BasicService(PagingAndSortingRepository pagingAndSortingRepository) {
        this.pagingAndSortingRepository = pagingAndSortingRepository;
    }

    public List<T> getList(Sort.Order... order) {
        Sort sort;
        if (order.length == 0) {
            Sort.Order idOrder = new Sort.Order(Sort.Direction.DESC, "id");
            sort = Sort.by(idOrder);
        } else {
            sort = Sort.by(order);
        }
        List<T> result = Lists.newArrayList(pagingAndSortingRepository.findAll(sort));
        return result.stream().filter(t -> !t.isDeleted()).collect(Collectors.toList());
    }

    public List<T> getListByPage(Pageable pageable, Sort.Order... order) {
        Sort sort;
        if (order.length == 0) {
            sort = new Sort(Sort.Direction.DESC, "id");
        } else {
            sort = Sort.by(order);
        }
        Pageable pageableNew = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);

        List<T> result = Lists.newArrayList(pagingAndSortingRepository.findAll(pageableNew));
        return result.stream().filter(t -> !t.isDeleted()).collect(Collectors.toList());
    }

    public T getById(ID id) {
        return pagingAndSortingRepository.findById(id).orElse(null);
    }

    public T saveOrUpdate(T t) {
        return pagingAndSortingRepository.save(t);
    }

    public Iterable<T> saveOrUpdateAll(List<T> list) {
        Iterable<T> result = pagingAndSortingRepository.saveAll(list);
        return result;
    }

    public void saveOrUpdateAll(Iterable<T> list) {
        pagingAndSortingRepository.saveAll(list);
    }

    public T deleteEntity(ID id) {
        T t = getById(id);
        t.setDeleted(true);
        return pagingAndSortingRepository.save(t);
    }

    public T delete(T t) {
        t.setDeleted(true);
        return pagingAndSortingRepository.save(t);
    }

    public Sort getSortBy(List<OrderRequest> order, Object o) {
        Sort sort;
        if (order == null || order.size() == 0) {
            sort = new Sort(Sort.Direction.DESC, "id");
        } else {
            List<Field> fields = Arrays.asList(o.getClass().getDeclaredFields());
            List<Sort.Order> orders = getOrder(fields,order);
            sort = getSort(orders.toArray(new Sort.Order[]{}));
        }
        return sort;
    }

    public Pageable getPageableBy(List<OrderRequest> order,Pageable pageable,Object o) {
        Sort sort = getSortBy(order,o);
        Pageable result = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        return result;
    }

    public List<Sort.Order> getOrder(List<Field> fields,List<OrderRequest> order) {
        List<String> properties = fields.stream().map(Field::getName).collect(Collectors.toList());
        List<Sort.Order> orders = Lists.newArrayList();
        for (OrderRequest orderRequest : order) {
            if (!properties.contains(orderRequest.getProperty())) {
                continue;
            }
            orders.add(new Sort.Order(orderRequest.getDirection(), orderRequest.getProperty()));
        }
        return orders;
    }

    public long getCount() {
        return pagingAndSortingRepository.count();
    }

    public Sort getSort(Sort.Order... order){
        Sort sort;
        if (order.length == 0) {
            Sort.Order idOrder = new Sort.Order(Sort.Direction.DESC, "id");
            sort = Sort.by(idOrder);
        } else {
            sort = Sort.by(order);
        }
        return sort;
    }

    public void connectStringBuffer(String str,StringBuffer stringBuffer) {
        if (!Strings.isNullOrEmpty(str)) {
            stringBuffer.append(str + "-");
        }
    }

    public String getSearchConditionLike(String searchCondition) {
        String searchConditionLike = "%";
        if (!Strings.isNullOrEmpty(searchCondition)) {
            searchConditionLike = "%\"searchCondition\":\"%" + searchCondition + "%";
        }
        return searchConditionLike;
    }
    public String getLikeBy (String str) {
        String strLike = "%";
        if (!Strings.isNullOrEmpty(str)) {
            strLike = "%" + str + "%";
        }
        return strLike;
    }

}
