package com.pyg.page.service;

import java.io.IOException;

/**
 * 商品详情页接口
 */
public interface ItemPageService {
    // 生成商品详情页
    boolean getItemHtml(Long goodsId) ;
}
