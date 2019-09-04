package org.dogoodthings.ectr.osgi.search.provider.changeNumber;

import org.dogoodthings.ectr.osgi.search.provider.material.MaterialSearchTask;

import com.dscsag.plm.spi.interfaces.search.SearchProvider;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchTask;

/**
 *
 */
public class ChangeNumberSearchProvider implements SearchProvider
{
  @Override
  public SearchTask createTask(SearchQuery searchQuery)
  {
    ChangeNumberSearchTask searchTask = new ChangeNumberSearchTask(searchQuery);
    return searchTask;
  }
}