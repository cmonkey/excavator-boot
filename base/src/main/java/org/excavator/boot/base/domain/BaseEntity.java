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
package org.excavator.boot.base.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Entity 基类
 */
public abstract class BaseEntity<T> implements Serializable {
    /**
     * 主键:ID
     */
    protected Long     id;

    /**
     * 当前实体分页对象
     */
    @JSONField(serialize = false)
    protected Page<T>  page;

    /**
     * 是否是新记录（默认：false），调用setNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    @JSONField(serialize = false)
    protected boolean  newRecord;

    /**
     * 关键字
     */
    @JSONField(serialize = false)
    protected String   keywords;

    /**
     * 日期类型  "":全部，01：近7天，02：近1个月，03：近3个月，04：近一年
     **/
    @JSONField(serialize = false)
    private String     dateType;
    /**
     * 开始日期
     **/
    @JSONField(serialize = false)
    private String     startTime;
    /**
     * 结束日期
     **/
    @JSONField(serialize = false)
    private String     endTime;

    /****************************导出相关属性****************************/
    /**
     * 导出标题
     */
    @JSONField(serialize = false)
    protected String   exportTitle;
    /**
     * 导出列显示
     */
    @JSONField(serialize = false)
    protected String[] hearders;
    /**
     * 导出列属性
     */
    @JSONField(serialize = false)
    protected String[] fields;

    /**
     * 空构造函数
     */
    public BaseEntity() {
        super();
    }

    /**
     * 构造函数
     *
     * @param uuid uuid
     */
    public BaseEntity(final String uuid) {
        super();
        //这里用idWorker生成
        //this.uuid = StringUtils.isBlank(uuid) ? genUuid() : uuid;
    }

    /**
     * 获得uuid
     *
     * @return id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * 是否是新记录（默认：false），调用setNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     *
     * @return isblank
     */
    @JsonIgnore
    public boolean isNewRecord() {
        return newRecord || isIdBlank();
    }

    /**
     * 判断id是否为空
     *
     * @return isblank
     */
    private boolean isIdBlank() {
        return id == null;
    }

    /**
     * 是否是新记录（默认：false），调用setNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     * @param newRecord record
     */
    public void setNewRecord(final boolean newRecord) {
        this.newRecord = newRecord;
    }

    /**
     * 获得当前实体分页对象
     *
     * @return page
     */
    @JsonIgnore
    @XmlTransient
    public Page<T> getPage() {
        return page;
    }

    /**
     * 设置当前实体分页对象
     *
     * @param page page
     * @return page
     */
    public Page<T> setPage(final Page<T> page) {
        this.page = page;
        return page;
    }

    /**
     * 保存数据库前预处理
     */
    public void preInsert() {
    }

    /**
     * 保存数据库前预更新
     */
    public void preUpdate() {
    }

    /**
     * 获得关键字
     *
     * @return keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 设置关键字
     *
     * @param keywords keyword
     */
    public void setKeywords(final String keywords) {
        this.keywords = StringUtils.isBlank(keywords) ? null : keywords.trim();
    }

    /**
     * excel导出标题
     *
     * @return exportTitle
     */
    public String getExportTitle() {
        return exportTitle;
    }

    /**
     * 设置excel导出标题
     * @param exportTitle exportTitle
     */
    public void setExportTitle(final String exportTitle) {
        this.exportTitle = exportTitle;
    }

    /**
     * 获得"导出列显示"
     *
     * @return harders
     */
    public String[] getHearders() {
        return hearders;
    }

    /**
     * 设置导出列显示
     *
     * @param hearders hearders
     */
    public void setHearders(final String hearders) {
        this.hearders = StringUtils.isBlank(hearders) ? null : hearders.split(",");
    }

    /**
     * 获得导出列属性
     *
     * @return fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * 导出列属性
     *
     * @param fields ids
     */
    public void setFields(final String fields) {
        this.fields = StringUtils.isBlank(fields) ? null : fields.split(",");
    }

    /**
     * 获得日期类型
     *
     * @return
    public String getDateType() {
        return CommonConstant.DATE_TYPE_ALL.equals(dateType) ? "" : dateType;
    }
    */

    /**
     * 设置日期类型
     *
     * @param dateType datetype
     */
    public void setDateType(final String dateType) {
        this.dateType = dateType;
    }

    /**
     * 获得开始时间
     *
     * @return
    public String getStartTime() {
        String temp = startTime;
        if (StringUtils.isNotBlank(temp)) {
            temp = DateUtils.getDateStart(DateUtils.parseDate(temp));
        }
        return temp;
    }
     */

    /**
     * 设置开始时间
     *
     * @param startTime starttime
     */
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获得结束时间
     *
     * @return
    public String getEndTime() {
        String temp = endTime;
        if (StringUtils.isNotBlank(temp)) {
            temp = DateUtils.getDateEnd(DateUtils.parseDate(temp));
        }
        return temp;
    }
     */

    /**
     * 设置结束时间
     *
     * @param endTime endtime
     */
    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    /**
     * 根据日期类型获得相应的开始时间
     *
     * @return
    public String getDateTypeTime() {
        Date dateTypeTime = null; //默认全部
        final String dateTypeTemp = getDateType();
        if (StringUtils.isNotBlank(dateTypeTemp)) {
            final Date now = DateUtils.getNow();
            if (CommonConstant.DATE_TYPE_WEEK.equals(dateTypeTemp)) {  //近7天
                dateTypeTime = DateUtils.rollDay(now, -7);
            } else if (CommonConstant.DATE_TYPE_MONTH.equals(dateTypeTemp)) {   //近1月
                dateTypeTime = DateUtils.rollMon(now, -1);
            } else if (CommonConstant.DATE_TYPE_QUARTER.equals(dateTypeTemp)) { //近3月
                dateTypeTime = DateUtils.rollMon(now, -3);
            } else if (CommonConstant.DATE_TYPE_YEAR.equals(dateTypeTemp)) {    //近1年
                dateTypeTime = DateUtils.rollYear(now, -1);
            }
        }
        return dateTypeTime == null ? null : DateUtils.getDateEnd(dateTypeTime);
    }
     */
    /**
     * 根据dateType设置相应的startTime和endTime
    public void putQueryTimeSection() {
        if (StringUtils.isNotBlank(this.getDateType())) {
            String endTime = DateUtils.dateStr4(DateUtils.getNow());
            String startTime = null;
            if (CommonConstant.DATE_TYPE_WEEK.equals(this.getDateType())) {  //近7天
                startTime = DateUtils.dateStr4(DateUtils.rollDay(DateUtils.getNow(), -7));
            } else if (CommonConstant.DATE_TYPE_MONTH.equals(this.getDateType())) {   //近1月
                startTime = DateUtils.dateStr4(DateUtils.rollMon(DateUtils.getNow(), -1));
            } else if (CommonConstant.DATE_TYPE_QUARTER.equals(this.getDateType())) { //近3月
                startTime = DateUtils.dateStr4(DateUtils.rollMon(DateUtils.getNow(), -3));
            } else if (CommonConstant.DATE_TYPE_YEAR.equals(this.getDateType())) {    //近1年
                startTime = DateUtils.dateStr4(DateUtils.rollYear(DateUtils.getNow(), -1));
            }
            this.setStartTime(startTime);
            this.setEndTime(endTime);
        }
    }
     */

}