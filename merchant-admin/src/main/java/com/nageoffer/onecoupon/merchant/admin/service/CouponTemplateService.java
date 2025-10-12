
package com.nageoffer.onecoupon.merchant.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.onecoupon.framework.result.Result;
import com.nageoffer.onecoupon.merchant.admin.dao.entity.CouponTemplateDO;
import com.nageoffer.onecoupon.merchant.admin.dto.req.CouponTemplateNumberReqDTO;
import com.nageoffer.onecoupon.merchant.admin.dto.req.CouponTemplatePageQueryReqDTO;
import com.nageoffer.onecoupon.merchant.admin.dto.req.CouponTemplateSaveReqDTO;
import com.nageoffer.onecoupon.merchant.admin.dto.resp.CouponTemplatePageQueryRespDTO;
import com.nageoffer.onecoupon.merchant.admin.dto.resp.CouponTemplateQueryRespDTO;

/**
 * 优惠券模板业务逻辑层
 */
public interface CouponTemplateService extends IService<CouponTemplateDO> {

    /**
     * 创建商家优惠券模板
     *
     * @param requestParam 请求参数
     */
    void createCouponTemplate(CouponTemplateSaveReqDTO requestParam);


    IPage<CouponTemplatePageQueryRespDTO> pageQueryCouponTemplate(CouponTemplatePageQueryReqDTO reqDTO);

    CouponTemplateQueryRespDTO findCouponTemplateById(String couponTemplateId);

    void increaseNumberCouponTemplate(CouponTemplateNumberReqDTO requestParam);

    void terminateCouponTemplate(String couponTemplateId);
}

