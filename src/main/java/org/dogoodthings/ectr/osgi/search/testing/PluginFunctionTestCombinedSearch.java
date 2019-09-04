package org.dogoodthings.ectr.osgi.search.testing;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import org.dogoodthings.ectr.osgi.ECTRServiceHolder;
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

    SearchQuery searchQuery = new DefaultSingleSearchQuery(50,searchText);
    SearchTask materialSearchTask = new MaterialSearchProvider().createTask(searchQuery);
    SearchTask changeNumberSearchTask = new ChangeNumberSearchProvider().createTask(searchQuery);

    SearchResult materialSearchResult = materialSearchTask.search();
    SearchResult changeNumberSearchResult = changeNumberSearchTask.search();

    List<SearchHit> materialHits = materialSearchResult.getHits();
    List<SearchHit> changeNumberHits = changeNumberSearchResult.getHits();
    List<SearchHit> limitedHits = Stream.concat(materialHits.stream(), changeNumberHits.stream())
        .sorted(Comparator.comparing(SearchHit::getScore).reversed())
        .limit(searchQuery.getMaxHits())
        .collect(Collectors.toList());

    limitedHits.stream().map(x -> x.getObjectKey().toString()).forEach(ECTRServiceHolder.getEctrService().getPlmLogger()::trace);
    pluginResponse = PluginResponseFactory.infoResponse("found " + limitedHits.size() + " objects.");
    return pluginResponse;
  }
}