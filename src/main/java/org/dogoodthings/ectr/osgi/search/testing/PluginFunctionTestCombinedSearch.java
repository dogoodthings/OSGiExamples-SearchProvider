package org.dogoodthings.ectr.osgi.search.testing;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.dogoodthings.ectr.osgi.ECTRServiceHolder;
import org.dogoodthings.ectr.osgi.search.ListCombiner;
import org.dogoodthings.ectr.osgi.search.provider.DefaultSingleSearchQuery;
import org.dogoodthings.ectr.osgi.search.provider.changeNumber.ChangeNumberSearchProvider;
import org.dogoodthings.ectr.osgi.search.provider.material.MaterialSearchProvider;

import com.dscsag.plm.spi.interfaces.gui.PluginFunction;
import com.dscsag.plm.spi.interfaces.gui.PluginRequest;
import com.dscsag.plm.spi.interfaces.gui.PluginResponse;
import com.dscsag.plm.spi.interfaces.gui.PluginResponseFactory;
import com.dscsag.plm.spi.interfaces.search.SearchHit;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchResult;
import com.dscsag.plm.spi.interfaces.search.SearchTask;

public class PluginFunctionTestCombinedSearch implements PluginFunction
{
  @Override
  public PluginResponse actionPerformed(PluginRequest pluginRequest)
  {
    PluginResponse pluginResponse;
    String searchText = JOptionPane.showInputDialog(null, "search for", "*815");

    Instant startTime = Instant.now();

    SearchQuery searchQuery = new DefaultSingleSearchQuery(50,searchText);
    SearchTask materialSearchTask = new MaterialSearchProvider().createTask(searchQuery);
    SearchTask changeNumberSearchTask = new ChangeNumberSearchProvider().createTask(searchQuery);

    SearchResult materialSearchResult = materialSearchTask.search();
    SearchResult changeNumberSearchResult = changeNumberSearchTask.search();

    List<SearchHit> materialHits = materialSearchResult.getHits();
    List<SearchHit> changeNumberHits = changeNumberSearchResult.getHits();
    List<SearchHit> limitedHits = ListCombiner.combineLists(Arrays.asList(materialHits, changeNumberHits),searchQuery.getMaxHits());
    limitedHits.stream().map(x -> x.getObjectKey().toString()).forEach(ECTRServiceHolder.getEctrService().getPlmLogger()::trace);
    Instant finishedTime = Instant.now();
    long timeElapsed = Duration.between(startTime,finishedTime).toMillis();
    String message = "found " + limitedHits.size() + " objects in "+timeElapsed+" ms.";
    ECTRServiceHolder.getEctrService().getPlmLogger().trace(message);
    pluginResponse = PluginResponseFactory.infoResponse(message);
    return pluginResponse;
  }



}