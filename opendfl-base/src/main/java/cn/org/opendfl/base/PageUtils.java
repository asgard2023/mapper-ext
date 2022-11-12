package cn.org.opendfl.base;


import cn.org.opendfl.exception.BaseException;
import cn.org.opendfl.exception.FailedException;
import cn.org.opendfl.exception.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PageUtils {
    static Logger logger = LoggerFactory.getLogger(PageUtils.class);

    private PageUtils() {

    }

    private static Integer limitPageSize = 1000;
    private static Integer limitPageMax = 1000;
    private static Integer limitPageTotalRow = 10000;
    private static Long limitLoadTime = 0L;

    public static Integer getLimitPageMax() {
        return limitPageMax;
    }

    /**
     * 初始化查询分页对象
     *
     * @param params
     * @return
     * @throws BaseException
     */
    public static <T> MyPageInfo<T> createPageInfo(Map<String, Object> params) throws BaseException {
        MyPageInfo<T> pageInfo = new MyPageInfo<>();
        Object pageNumObj = params.get("pageNum");
        if (pageNumObj == null) {
            pageNumObj = params.get("page");
        }
        Object pageSizeObj = params.get("pageSize");
        ValidateUtils.notNull(pageNumObj, "请输入页码参数page(或者pageNum)");
        ValidateUtils.notNull(pageSizeObj, "请输入页长参数pageSize");
        pageInfo.setPageNum(Integer.parseInt(pageNumObj.toString()));
        pageInfo.setPageSize(Integer.parseInt(pageSizeObj.toString()));
        if (pageInfo.getPageSize() > limitPageSize) {
            logger.warn("----createPageInfo--pageSize={} outLimit", pageInfo.getPageSize());
            throw new FailedException("pageSize out limit");
        }
        if (pageInfo.getPageNum() > limitPageMax) {
            logger.warn("----createPageInfo--pageNum={} outLimit", pageInfo.getPageNum());
            throw new FailedException("pageNum out limit");
        }
        int totalRow = pageInfo.getPageNum() * pageInfo.getPageSize();
        if (totalRow > limitPageTotalRow) {
            logger.warn("----createPageInfo--totalRow={} outLimit", totalRow);
            throw new FailedException("pageNum out limit");
        }


        return pageInfo;
    }

    /**
     * 初始化查询分页对象
     *
     * @param params
     * @return
     * @throws BaseException
     */
    public static <T> MyPageInfo<T> createPageInfoByfeign(Map<String, Object> params) throws BaseException {
        MyPageInfo<T> pageInfo = new MyPageInfo<>();
        Object pageNumObj = params.get("pageNum");
        if (pageNumObj == null) {
            pageNumObj = params.get("page");
        }
        Object pageSizeObj = params.get("pageSize");
        ValidateUtils.notNull(pageNumObj, "请输入页码参数page(或者pageNum)");
        ValidateUtils.notNull(pageSizeObj, "请输入页长参数pageSize");
        pageInfo.setPageNum(Integer.parseInt(pageNumObj.toString()));
        pageInfo.setPageSize(Integer.parseInt(pageSizeObj.toString()));
        return pageInfo;
    }

    public static <T> MyPageInfo<T> createPageInfo(int pageNum, int pageSize) throws BaseException {
        MyPageInfo<T> pageInfo = new MyPageInfo<>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        if (pageInfo.getPageSize() > limitPageSize) {
            logger.warn("----createPageInfo--pageSize={} outLimit", pageInfo.getPageSize());
            throw new FailedException("pageSize out limit");
        }
        if (pageInfo.getPageNum() > limitPageMax) {
            logger.warn("----createPageInfo--pageNum={} outLimit", pageInfo.getPageNum());
            throw new FailedException("pageNum out limit");
        }
        int totalRow = pageInfo.getPageNum() * pageInfo.getPageSize();
        if (totalRow > limitPageTotalRow) {
            logger.warn("----createPageInfo--totalRow={} outLimit", totalRow);
            throw new FailedException("pageNum out limit");
        }
        return pageInfo;
    }

}
