package com.lh.daily.service;

import com.lh.daily.NotFoundException;
import com.lh.daily.dao.TypeRepository;
import com.lh.daily.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Transactional //事务，保持数据的一致性
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        //把type的值赋给t对象
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
