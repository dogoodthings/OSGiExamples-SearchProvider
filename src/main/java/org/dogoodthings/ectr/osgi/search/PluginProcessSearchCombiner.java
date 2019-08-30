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

import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.process.ContainerKey;
import com.dscsag.plm.spi.interfaces.process.DefaultPluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;

/**

 */
public class PluginProcessSearchCombiner extends PluginProcessSearch
{
  public static ContainerKey<List<PluginProcessSearch>> IN_SEARCH_PROCESSES = new ContainerKey<>("SEARCH_PROCESSES");

  @Override
  public PluginProcessContainer execute(PluginProcessContainer pluginProcessContainer) throws Exception
  {
    String searchTerm = pluginProcessContainer.getParameter(IN_SEARCH_TERM);
    int maxHits = pluginProcessContainer.getParameter(IN_MAX_HITS);
    List<PluginProcessSearch> processes = pluginProcessContainer.getParameter(IN_SEARCH_PROCESSES);
    ExecutorService executorService = Executors.newFixedThreadPool(processes.size());

    List<List<PlmObjectKey>> collect = processes.stream()
        .map(x -> new SearchCallable(searchTerm, maxHits, x))
        .map(executorService::submit)
        .map(this::resolveFuture)
        .collect(Collectors.toList());
    executorService.shutdown();

    List<PlmObjectKey> combinedList = combineResult(collect, maxHits);

    DefaultPluginProcessContainer returnContainer = new DefaultPluginProcessContainer();
    returnContainer.setParameter(OUT_FOUND_KEYS,combinedList);
    return returnContainer;
  }

  private List<PlmObjectKey> resolveFuture(Future<List<PlmObjectKey>> future)
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

  private List<PlmObjectKey> combineResult(List<List<PlmObjectKey>> collect, int maxHits)
  {
    LinkedHashSet<PlmObjectKey> set = new LinkedHashSet<>();
    for(int i=0;i<maxHits && set.size()<=maxHits;i++)
    {
      for(List<PlmObjectKey> list:collect)
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

  private final static class SearchCallable implements Callable<List<PlmObjectKey>>
  {
    String searchTerm;
    int maxHits;
    PluginProcessSearch pluginProcessSearch;

    SearchCallable(String searchTerm, int maxHits, PluginProcessSearch searchProcess)
    {
      this.maxHits=maxHits;
      this.searchTerm=searchTerm;
      this.pluginProcessSearch=searchProcess;
    }

    @Override
    public List<PlmObjectKey> call() throws Exception
    {
      DefaultPluginProcessContainer pluginProcessContainer = new DefaultPluginProcessContainer();
      pluginProcessContainer.setParameter(IN_SEARCH_TERM,searchTerm);
      pluginProcessContainer.setParameter(IN_MAX_HITS,maxHits);
      PluginProcessContainer returnContainer = pluginProcessSearch.execute(pluginProcessContainer);
      return returnContainer.getParameter(OUT_FOUND_KEYS);
    }
  }
}