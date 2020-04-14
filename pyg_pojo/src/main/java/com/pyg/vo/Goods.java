package com.pyg.vo;

import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    private TbGoods goods;

    private TbGoodsDesc goodsDesc;

    private List<TbItem> tbItemList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getTbItemList() {
        return tbItemList;
    }

    public void setTbItemList(List<TbItem> tbItemList) {
        this.tbItemList = tbItemList;
    }
}
