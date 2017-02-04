package io.querydsl.utils;

import org.springframework.data.domain.Sort;

/**
 * Created by Davis on 17/2/3.
 */
public final class SortBuilder {
  public static Sort buildSortCriteria(String sortCriteria) {
    Sort result = null;

    //    Sort.Order order = new Sort.Order(Sort.Direction.ASC, "age");
    Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "defaultAddress.city");
    Sort.Order nameOrder = new Sort.Order(Sort.Direction.ASC, "name.text");
//    Sort sort = new Sort(order, order1);

//    Sort sort = new Sort(order1);
    result = new Sort(nameOrder);

    return result;
  }
}
