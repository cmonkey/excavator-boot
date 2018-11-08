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
package org.excavator.boot.base.mapper;

import java.util.List;

/**
 * DAO支持类实现
 */
public interface BaseMapper<T> {

    /**
     * 插入数据
     * @param entity object
     * @return row num
     */
    int insert(T entity);

    /**
     * 批量插入
     * @param list object
     * @return row num
     */
    int insertBatch(List<T> list);

    /**
     * 删除数据
     * @param id 编号
     * @return row num
     */
    int delete(Long id);

    /**
     * 批量删除
     * @param ids 编号列表
     * @return row num
     */
    int deleteBatch(Long[] ids);

    /**
     * 逻辑删除（更新is_delete字段为1）
     * @param id 编号
     * @return row num
     */
    int deleteLogic(Long id);

    /**
     * 更新数据
     * @param entity object
     * @return row num
     */
    int update(T entity);

    /**
     * 批量更新
     * @param list object
     * @return  row num
     */
    int updateBatch(List<T> list);

    /**
     * 查询所有数据列表
     * @return list object
     */
    List<T> findAll();

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，example model.setPage(new Page<T>())
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
     * @param id num
     * @return object
     */
    T get(Long id);

}