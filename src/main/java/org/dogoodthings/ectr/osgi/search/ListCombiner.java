package org.dogoodthings.ectr.osgi.search;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 */
public class ListCombiner
{
  public static <T> List<T> combineLists(List<List<T>> lists, int maxLength)
  {
    LinkedHashSet<T> resultSet = new LinkedHashSet<>();
    for(int i=0; i<maxLength && resultSet.size()<maxLength; i++)
    {
      for(List<T> list: lists)
      {
        if(list.size()>i && resultSet.size()<maxLength)
          resultSet.add(list.get(i));
      }
    }
    return new ArrayList<>(resultSet);
  }
}
