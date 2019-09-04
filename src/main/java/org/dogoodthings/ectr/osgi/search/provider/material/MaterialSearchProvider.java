package org.dogoodthings.ectr.osgi.search.provider.material;

import com.dscsag.plm.spi.interfaces.search.SearchProvider;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchTask;

/**
 *
 *
 */
public class MaterialSearchProvider implements SearchProvider
{
  @Override
  public SearchTask createTask(SearchQuery searchQuery)
  {
    MaterialSearchTask searchTask = new MaterialSearchTask(searchQuery);
    return searchTask;
  }
}