package com.hengxin.mall.test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * author : fflin
 * date   : 2020/5/11 17:24
 * desc   : LRU 算法测试
 *          最近访问的(CRU)数据移动到最顶部
 * version: 1.0
 */
public class LRU<K,V> {

    // 当前缓存
    private int curCacheSize;

    // 缓存总容量
    private int totalSize;

    //LinkList
    private LinkedHashMap<K, CacheNode> caches; // 使用for循环测试结果顺序有问题

    // 开始 结束
    private CacheNode first, last;


    public LRU(int total) {
        curCacheSize = 0;
        this.totalSize = total;
        caches = new LinkedHashMap<>(total);
    }


    public void putValue(K key, V val) {
        CacheNode node = caches.get(key);
        if (node == null) {
            if (caches.size() >= totalSize) {
                caches.remove(last.key);
                removeLast();
            }

            node = new CacheNode();
            node.key = key;

        }

        node.value = val;
        //把添加的数据移动到顶部
        moveToHead(node);
        caches.put(key,node);
    }

    private void moveToHead(CacheNode node) {
        if (first == node) {
            return;
        }

        if(node.next != null){
            node.next.pre = node.pre;
        }

        if(node.pre != null){
            node.pre.next = node.next;
        }

        if(node == last){
            last= last.pre;
        }
        if(first == null || last == null){
            first = last = node;
            return;
        }

        node.next=first;
        first.pre = node;
        first = node;
        first.pre = null;

    }

    private void removeLast() {
        if(last != null){
            last = last.pre;
            if(last == null){
                first = null;
            }else{
                last.next = null;
            }
        }
    }

    public Map<K, CacheNode> getCaches() {
        return caches;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        CacheNode node = first;
        while(node != null){
            sb.append(String.format("%s:%s ", node.key,node.value));
            node = node.next;
        }

        return sb.toString();
    }

}

class CacheNode{

    CacheNode pre;
    CacheNode next;
    Object key;
    Object value;
    public CacheNode(){

    }
}
