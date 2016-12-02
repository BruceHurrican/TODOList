/*
 * BruceHurrican
 * Copyright (c) 2016.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    This document is Bruce's individual learning the android demo, wherein the use of the code from the Internet, only to use as a learning exchanges.
 *    And where any person can download and use, but not for commercial purposes.
 *    Author does not assume the resulting corresponding disputes.
 *    If you have good suggestions for the code, you can contact BurrceHurrican@foxmail.com
 *    本文件为Bruce's个人学习android的作品, 其中所用到的代码来源于互联网，仅作为学习交流使用。
 *    任和何人可以下载并使用, 但是不能用于商业用途。
 *    作者不承担由此带来的相应纠纷。
 *    如果对本代码有好的建议，可以联系BurrceHurrican@foxmail.com
 */

package bruce.kk.brucetodos.database.bean;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "t_CostMonth".
 */
@Entity(nameInDb = "t_CostMonth")
public class CostMonth implements Comparable<CostMonth> {

    @Id(autoincrement = true)
    @Unique
    public Long id;

    @Property(nameInDb = "costDay")
    public int costDay;

    @Property(nameInDb = "costTitle")
    @NotNull
    public String costTitle;

    @Property(nameInDb = "costDetail")
    public String costDetail;

    @Property(nameInDb = "costPrice")
    @NotNull
    public String costPrice;

    @Property(nameInDb = "costModifyDate")
    @NotNull
    public java.util.Date costModifyDate;

    @Generated(hash = 1055042100)
    public CostMonth() {
    }

    public CostMonth(Long id) {
        this.id = id;
    }

    @Generated(hash = 18941437)
    public CostMonth(Long id, int costDay, @NotNull String costTitle, String costDetail, @NotNull String costPrice, @NotNull java.util.Date costModifyDate) {
        this.id = id;
        this.costDay = costDay;
        this.costTitle = costTitle;
        this.costDetail = costDetail;
        this.costPrice = costPrice;
        this.costModifyDate = costModifyDate;
    }

    @Override
    public int compareTo(@NonNull CostMonth another) {
        if (this.costDay != another.costDay) {
//            return this.costDay - another.costDay; // 按 costDay 升序
            return another.costDay - this.costDay; // 按 costDay 降序
        } else {
            // 按 costModifyDate.getTime() 升序
//            return (int) (this.costModifyDate.getTime() - another.costModifyDate.getTime());
            // 按 costModifyDate.getTime() 降序
            return (int) (another.costModifyDate.getTime() - this.costModifyDate.getTime());
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCostDay() {
        return this.costDay;
    }

    public void setCostDay(int costDay) {
        this.costDay = costDay;
    }

    public String getCostTitle() {
        return this.costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }

    public String getCostDetail() {
        return this.costDetail;
    }

    public void setCostDetail(String costDetail) {
        this.costDetail = costDetail;
    }

    public String getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public java.util.Date getCostModifyDate() {
        return this.costModifyDate;
    }

    public void setCostModifyDate(java.util.Date costModifyDate) {
        this.costModifyDate = costModifyDate;
    }
}