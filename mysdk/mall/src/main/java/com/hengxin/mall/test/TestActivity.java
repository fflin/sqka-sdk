package com.hengxin.mall.test;

import android.support.v7.widget.RecyclerView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.ui.order.adapter.MallOrderItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * author : fflin
 * date   : 2020/4/28 18:27
 * desc   :
 * version: 1.0
 */
public class TestActivity extends BaseActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView orderRv;

    @Override
    protected void initData() {
        MallOrderItem item = new MallOrderItem(this, 0);
        VLRAdapter adapter = new VLRAdapter(item);
        orderRv.setLayoutManager(new CrashBugLinearLayoutManager(this));
        orderRv.setAdapter(adapter);

        adapter.reLoadData(new TestUtil().getTestList());
    }

    @Override
    protected void initView() {
        refreshLayout = findViewById(R.id.order_smart_layout);
        orderRv = findViewById(R.id.order_recycler);
        testLRU();
    }

    private void testLRU() {

        LRU<String,String> lru = new LRU<String,String>(3);
        lru.putValue("key 1","value 1");
        System.out.println(lru.toString());
        lru.putValue("key 2","value 2");
        System.out.println(lru.toString());
        lru.putValue("key 3","value 3");
        System.out.println(lru.toString());
        lru.putValue("key 4","value 4");
        System.out.println(lru.toString());
        lru.putValue("key 5","value 5");
        System.out.println(lru.toString());

        ////！！！！！注意不要for循环添加获取测试
        /*for (int i = 0; i < 5; i++) {
            lru.putValue("key-"+i, "value-"+i);
        }*/

        /*Map<String, CacheNode> map = lru.getCaches();
        for (Map.Entry entry :  map.entrySet()) {
            System.out.println("key = " + entry.getKey()+"; "+((CacheNode)entry.getValue()).value);
        }*/

        System.out.println("--------------------------源码--------------------------------------------------");
        LRUCache<Integer,String> lruCache = new LRUCache<Integer,String>(3);
        lruCache.put(1,"value 1");
        System.out.println(lruCache.toString());
        lruCache.put(2,"value 2");
        System.out.println(lruCache.toString());
        lruCache.put(3,"value 3");
        System.out.println(lruCache.toString());
        lruCache.put(4,"value 4");
        System.out.println(lruCache.toString());
        lruCache.put(5,"value 5");
        System.out.println(lruCache.toString());

    }

    @Override
    protected int setLayout() {
        return R.layout.fm_order_recycler;
    }
}
