package org.dogoodthings.ectr.osgi.search;

import org.dogoodthings.ectr.osgi.ECTRServiceHolder;

import com.dscsag.plm.spi.interfaces.process.DefaultPluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;
import com.dscsag.plm.spi.interfaces.rfc.RfcExecutor;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

public abstract class PluginProcessRfcSearch extends PluginProcessSearch
{
  @Override
  public PluginProcessContainer execute(PluginProcessContainer pluginProcessContainer) throws Exception
  {
    SearchQuery searchQuery = pluginProcessContainer.getParameter(IN_SEARCH_QUERY);
    RfcExecutor rfcExecutor = ECTRServiceHolder.getEctrService().getRfcExecutor();
    RfcResult rfcResult = rfcExecutor.execute(prepareRfcCallBuilder(searchQuery).toRfcCall());
    SearchResult searchResult = createResult(rfcResult);
    DefaultPluginProcessContainer container = new DefaultPluginProcessContainer();
    container.setParameter(OUT_SEARCH_RESULT,searchResult);
    return container;
  }

  protected abstract RfcCallBuilder prepareRfcCallBuilder( SearchQuery searchQuery);

  protected abstract SearchResult createResult(RfcResult rfcResult);

}