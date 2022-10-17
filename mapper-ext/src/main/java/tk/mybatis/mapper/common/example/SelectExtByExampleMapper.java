/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.mapper.common.example;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.ext.vo.DataCountVo;
import tk.mybatis.mapper.provider.ExampleExtProvider;

import java.util.Date;

/**
 * 通用Mapper接口,Example查询
 *
 * @author chenjh
 */
public interface SelectExtByExampleMapper<T> {

    /**
     * 主要用于检查数据是否存在，直接取第一条，免count
     * select exists(select 1 from table_name limit 1)
     * @param example
     * @return
     */
    @SelectProvider(type = ExampleExtProvider.class, method = "dynamicSQL")
    Integer selectCountExistByExample(Object example);

    /**
     * SELECT count(*) count, max(create_time) maxTime
     * @param example
     * @return
     */
    @SelectProvider(type = ExampleExtProvider.class, method = "dynamicSQL")
    DataCountVo selectCountAndMaxTimeByExample(Object example);

    /**
     * SELECT count(*) count, min(create_time) minTime
     * @param example
     * @return
     */
    @SelectProvider(type = ExampleExtProvider.class, method = "dynamicSQL")
    DataCountVo selectCountAndMinTimeByExample(Object example);

    /**
     * SELECT count(*) count, min(create_time) minTime, max(create_time) maxTime
     * @param example
     * @return
     */
    @SelectProvider(type = ExampleExtProvider.class, method = "dynamicSQL")
    DataCountVo selectCountAndMinMaxTimeByExample(Object example);

    /**
     * SELECT max(create_time)
     *
     * @param example
     * @return
     */
    @SelectProvider(type = ExampleExtProvider.class, method = "dynamicSQL")
    Date selectMaxCreateTimeByExample(Object example);

    /**
     * SELECT max(modify_time)
     *
     * @param example
     * @return
     */
    @SelectProvider(type = ExampleExtProvider.class, method = "dynamicSQL")
    Date selectMaxModifyDateByExample(Object example);

}