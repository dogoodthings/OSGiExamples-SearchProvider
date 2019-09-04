package org.dogoodthings.ectr.osgi.search;

import com.dscsag.plm.spi.interfaces.process.ContainerKey;
import com.dscsag.plm.spi.interfaces.process.PluginProcess;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchResult;

public abstract class PluginProcessSearch implements PluginProcess
{
  public static ContainerKey<SearchQuery> IN_SEARCH_QUERY = new ContainerKey<>("SEARCH_QUERY");
  public static ContainerKey<SearchResult> OUT_SEARCH_RESULT = new ContainerKey<>("SEARCH_RESULT");
}