package org.dogoodthings.ectr.osgi.search.material;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.dogoodthings.ectr.osgi.search.PluginProcessSearch;

import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.process.DefaultPluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;

/**
 * combines search material by description and by number (number OR description)
 * by executing two searches in parallel using executor service
 */
public class PluginProcessSearchMaterialCombined extends PluginProcessSearch
{
  @Override
  public PluginProcessContainer execute(PluginProcessContainer pluginProcessContainer) throws Exception
  {
    String searchTerm = pluginProcessContainer.getParameter(IN_SEARCH_TERM);
    int maxHits = pluginProcessContainer.getParameter(IN_MAX_HITS);
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Future<List<PlmObjectKey>> first = executorService.submit(new SearchCallable(searchTerm,maxHits,new PluginProcessSearchMaterialByDescription()));
    Future<List<PlmObjectKey>> second = executorService.submit(new SearchCallable(searchTerm,maxHits,new PluginProcessSearchMaterialByNumber()));

    List<PlmObjectKey> firstList = first.get();
    List<PlmObjectKey> secondList = second.get();

    executorService.shutdown();

    List<PlmObjectKey> combinedList = combineResult(firstList,secondList);

    DefaultPluginProcessContainer returnContainer = new DefaultPluginProcessContainer();
    returnContainer.setParameter(OUT_FOUND_KEYS,combinedList);
    return returnContainer;
  }

  private List<PlmObjectKey> combineResult(List<PlmObjectKey> firstList, List<PlmObjectKey> secondList)
  {
    LinkedHashSet<PlmObjectKey> set = new LinkedHashSet<>();
    firstList.stream().forEach(set::add);
    secondList.stream().forEach(set::add);
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