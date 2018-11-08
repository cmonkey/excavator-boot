/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.base.service;

import org.excavator.boot.base.mapper.BaseMapper;
import org.excavator.boot.base.constant.Constant;
import org.excavator.boot.base.domain.BaseEntity;
import org.excavator.boot.base.domain.Page;
import org.excavator.boot.common.exception.BusinessException;
import org.excavator.boot.idworker.generator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BaseServiceImpl<D extends BaseMapper<T>, T extends BaseEntity<T>> implements
                                                                               BaseService<T> {

    @Resource
    protected IdGenerator idGenerator;
    /**
     * 持久层对象
     */
    @Autowired
    protected D           dao;

    @Override
    public T get(Long id) {
        return dao.get(id);
    }

    @Override
    public List<T> findList(T model) {
        return dao.findList(model);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public int getCount(T model) {
        return dao.getCount(model);
    }

    @Override
    public Page<T> findPage(T entity) {
        Page<T> page = entity.getPage();
        if (page == null) {
            page = new Page<T>();
        }
        page.setRows(dao.findList(entity));
        return page;
    }

    /***************************** 增删改操作 *****************************/

    @Transactional(readOnly = false)
    @Override
    public int insert(T entity) {
        //entity.setId(idGenerator.nextId());
        return dao.insert(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public int insertBatch(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        //批量提交的子列表
        int rows = 0;
        List<T> subList = new ArrayList<T>();
        for (final T t : list) {
            //t.setId(idGenerator.nextId());
            subList.add(t);
            //子列表已满,批量提交
            if (subList.size() == Constant.BATCH_OPERATION_COUNT) {
                rows += dao.insertBatch(subList);
                subList = new ArrayList<T>();
            }
        }
        //子列表未满的部分,做一次批量提交
        if (subList.size() > 0 && subList.size() < Constant.BATCH_OPERATION_COUNT) {
            rows += dao.insertBatch(subList);
        }
        return rows;
    }

    @Transactional(readOnly = false)
    @Override
    public void save(T entity) {
        int result;
        if (entity.isNewRecord()) {
            entity.setId(idGenerator.nextId());
            result = dao.insert(entity);
        } else {
            entity.preUpdate();
            result = dao.update(entity);
        }
        if (result == Constant.INT_ZERO) {
            throw new BusinessException("No saved records");
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void update(T entity) {
        entity.preUpdate();
        int result = dao.update(entity);
        if (result == Constant.INT_ZERO) {
            throw new BusinessException("No updated records");
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void updateBatch(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //批量提交的子列表
        List<T> subList = new ArrayList<T>();
        for (final T t : list) {
            t.preUpdate();
            subList.add(t);
            //子列表已满,批量提交
            if (subList.size() == Constant.BATCH_OPERATION_COUNT) {
                int result = dao.updateBatch(subList);
                if (result == Constant.INT_ZERO) {
                    throw new BusinessException("No updated records");
                }
                subList = new ArrayList<T>();
            }
        }
        //子列表未满的部分,做一次批量提交
        if (subList.size() > 0 && subList.size() < Constant.BATCH_OPERATION_COUNT) {
            int result = dao.updateBatch(subList);
            if (result == Constant.INT_ZERO) {
                throw new BusinessException("No updated records");
            }
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(final Long id) {
        int result = dao.delete(id);
        if (result == Constant.INT_ZERO) {
            throw new BusinessException("Record not deleted, id=" + id);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteBatch(final Long[] ids) {
        int result = dao.deleteBatch(ids);
        if (result == Constant.INT_ZERO) {
            throw new BusinessException("Records not deleted, ids=" + Arrays.toString(ids));
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteLogic(final Long id) {
        final int result = dao.deleteLogic(id);
        if (result == Constant.INT_ZERO) {
            throw new BusinessException("Record not deleted, id=" + id);
        }
    }

    @Override
    public Optional<T> find(Long id) {
        return Optional.ofNullable(dao.get(id));
    }

}