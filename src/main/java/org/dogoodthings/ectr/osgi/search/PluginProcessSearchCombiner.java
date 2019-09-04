package org.dogoodthings.ectr.osgi.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.dscsag.plm.spi.interfaces.process.ContainerKey;
import com.dscsag.plm.spi.interfaces.process.DefaultPluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;
import com.dscsag.plm.spi.interfaces.search.SearchHit;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;

/**

 */
public class PluginProcessSearchCombiner extends PluginProcessSearch
{
  public static ContainerKey<List<PluginProcessSearch>> IN_SEARCH_PROCESSES = new ContainerKey<>("SEARCH_PROCESSES");

  @Override
  public PluginProcessContainer execute(PluginProcessContainer pluginProcessContainer) throws Exception
  {
    SearchQuery searchQuery = pluginProcessContainer.getParameter(IN_SEARCH_QUERY);
    int maxHits = searchQuery.getMaxHits();
    List<PluginProcessSearch> processes = pluginProcessContainer.getParameter(IN_SEARCH_PROCESSES);
    ExecutorService executorService = Executors.newFixedThreadPool(processes.size());

    List<List<SearchHit>> collect = processes.stream()
        .map(x -> new SearchCallable(searchQuery, x))
        .map(executorService::submit)
        .map(this::resolveFuture)
        .collect(Collectors.toList());
    executorService.shutdown();

    List<SearchHit> combinedList = combineResult(collect, maxHits);

    DefaultPluginProcessContainer returnContainer = new DefaultPluginProcessContainer();
    returnContainer.setParameter(OUT_SEARCH_RESULT, () -> combinedList);
    return returnContainer;
  }

  private List<SearchHit> resolveFuture(Future<List<SearchHit>> future)
  {
    try
    {
      return future.get();
    }
    catch (Exception e)
    {
      return Collections.emptyList();
    }
  }

  private List<SearchHit> combineResult(List<List<SearchHit>> collect, int maxHits)
  {
    LinkedHashSet<SearchHit> set = new LinkedHashSet<>();
    for(int i=0;i<maxHits && set.size()<=maxHits;i++)
    {
      for(List<SearchHit> list:collect)
      {
        if(set.size()<maxHits)
        {
          if (i < list.size())
            set.add(list.get(i));
        }
      }
    }
    return new ArrayList<>(set);
  }

  private final static class SearchCallable implements Callable<List<SearchHit>>
  {
    SearchQuery searchQuery;
    PluginProcessSearch pluginProcessSearch;

    SearchCallable(SearchQuery searchQuery, PluginProcessSearch searchProcess)
    {
      this.searchQuery=searchQuery;
      this.pluginProcessSearch=searchProcess;
    }

    @Override
    public List<SearchHit> call() throws Exception
    {
      DefaultPluginProcessContainer pluginProcessContainer = new DefaultPluginProcessContainer();
      pluginProcessContainer.setParameter(IN_SEARCH_QUERY,searchQuery);
      PluginProcessContainer returnContainer = pluginProcessSearch.execute(pluginProcessContainer);
      return returnContainer.getParameter(OUT_SEARCH_RESULT).getHits();
    }
  }
}