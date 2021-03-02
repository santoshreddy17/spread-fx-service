package com.fx.trade.app.data;

/**
 * Data Repository Interface.
 * @param <T>
 */
public interface DataRepository<T> {

//    /**
//     * Add.
//     * @param t
//     */
//    void add(T t);
//
//    /**
//     * Delete.
//     * @param id
//     */
//    void delete(String id);
//
//    /**
//     * Replace.
//     * @param t
//     */
//    void replace(T t);

    /**
     * Get.
     * @param id
     * @return
     */
    T get(String id);

}
