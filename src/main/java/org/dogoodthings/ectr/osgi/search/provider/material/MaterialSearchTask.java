package org.dogoodthings.ectr.osgi.search.provider.material;

import java.util.ArrayList;
import java.util.List;

import org.dogoodthings.ectr.osgi.ECTRServiceHolder;
import org.dogoodthings.ectr.osgi.search.PluginProcessSearch;
import org.dogoodthings.ectr.osgi.search.PluginProcessSearchCombiner;
import org.dogoodthings.ectr.osgi.search.material.PluginProcessSearchMaterialByDescription;
import org.dogoodthings.ectr.osgi.search.material.PluginProcessSearchMaterialByNumber;

import com.dscsag.plm.spi.interfaces.process.DefaultPluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchResult;
import com.dscsag.plm.spi.interfaces.search.SearchTask;

/**
 *
 *
 */
public class MaterialSearchTask implements SearchTask
{
  private SearchQuery searchQuery;
  MaterialSearchTask(SearchQuery searchQuery)
  {
    this.searchQuery = searchQuery;
  }

  @Override
  public SearchResult search()
  {
    SearchResult searchResult = null;
    DefaultPluginProcessContainer container = new DefaultPluginProcessContainer();
    container.setParameter(PluginProcessSearch.IN_SEARCH_QUERY,searchQuery);
    List<PluginProcessSearch> processes = new ArrayList<>();
    processes.add(new PluginProcessSearchMaterialByDescription());
    processes.add(new PluginProcessSearchMaterialByNumber());
    container.setParameter(PluginProcessSearchCombiner.IN_SEARCH_PROCESSES, processes);
    PluginProcessSearchCombiner processSearchCombined = new PluginProcessSearchCombiner();
    try
    {
      PluginProcessContainer result = processSearchCombined.execute(container);
      searchResult = result.getParameter(PluginProcessSearch.OUT_SEARCH_RESULT);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return searchResult;
  }

  @Override
  public void cancel()
  {
    ECTRServiceHolder.getEctrService().getPlmLogger().trace("material search: cancel is not implemented...");
  }
}