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

import org.excavator.boot.base.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    /**
     * 插入数据
     * @param entity object
     * @return row
     */
    int insert(T entity);

    /**
     * 批量插入
     * @param list object
     */
    int insertBatch(List<T> list);

    /**
     * 删除数据
     * @param id 编号
     * @return
     */
    void delete(Long id);

    /**
     * 批量删除
     * @param ids 编号列表
     * @return
     */
    void deleteBatch(Long[] ids);

    /**
     * 逻辑删除
     * @param id 编号
     * @return
     */
    void deleteLogic(Long id);

    /**
     * 更新数据
     * @param entity object
     * @return
     */
    void update(T entity);

    /**
     * 批量更新
     * @param list 列表
     */
    void updateBatch(List<T> list);

    /**
     * 查询所有数据列表
     * @return list
     */
    List<T> findAll();

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：model.setPage(new Page<T>());
     * @param model object
     * @return list
     */
    List<T> findList(T model);

    /**
     * 查询数据记录
     * @param model object
     * @return count
     */
    int getCount(T model);

    /**
     * 获取单条数据
     * @param id 编号
     * @return T
     */
    T get(Long id);

    Optional<T> find(Long id);

    /**
     * 保存数据
     * @param entity 实体
     */
    void save(T entity);

    /**
     * 查询分页数据
     * @param entity 实体
     * @return Page
     */
    Page<T> findPage(T entity);

}
