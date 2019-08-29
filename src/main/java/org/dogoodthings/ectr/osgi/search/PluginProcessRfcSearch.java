package org.dogoodthings.ectr.osgi.search;

import java.util.List;

import org.dogoodthings.ectr.osgi.ECTRServiceHolder;

import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.process.DefaultPluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;
import com.dscsag.plm.spi.interfaces.rfc.RfcExecutor;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

public abstract class PluginProcessRfcSearch extends PluginProcessSearch
{
  @Override
  public PluginProcessContainer execute(PluginProcessContainer pluginProcessContainer) throws Exception
  {
    String searchTerm = pluginProcessContainer.getParameter(IN_SEARCH_TERM);
    int maxHits = pluginProcessContainer.getParameter(IN_MAX_HITS);
    RfcExecutor rfcExecutor = ECTRServiceHolder.getEctrService().getRfcExecutor();
    RfcResult rfcResult = rfcExecutor.execute(prepareRfcCallBuilder(searchTerm,maxHits).toRfcCall());
    List<PlmObjectKey> searchResult = createResult(rfcResult);
    DefaultPluginProcessContainer container = new DefaultPluginProcessContainer();
    container.setParameter(OUT_FOUND_KEYS,searchResult);
    return container;
  }

  protected abstract RfcCallBuilder prepareRfcCallBuilder(String searchTerm, int maxHits);

  protected abstract List<PlmObjectKey> createResult(RfcResult rfcResult);

}