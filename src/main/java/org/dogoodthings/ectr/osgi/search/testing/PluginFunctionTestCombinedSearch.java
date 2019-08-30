package org.dogoodthings.ectr.osgi.search.testing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.dogoodthings.ectr.osgi.ECTRServiceHolder;
import org.dogoodthings.ectr.osgi.search.PluginProcessSearch;
import org.dogoodthings.ectr.osgi.search.PluginProcessSearchCombiner;
import org.dogoodthings.ectr.osgi.search.changeNumber.PluginProcessSearchChangeNumberByDescription;
import org.dogoodthings.ectr.osgi.search.changeNumber.PluginProcessSearchChangeNumberByNumber;
import org.dogoodthings.ectr.osgi.search.material.PluginProcessSearchMaterialByDescription;
import org.dogoodthings.ectr.osgi.search.material.PluginProcessSearchMaterialByNumber;

import com.dscsag.plm.spi.interfaces.gui.PluginFunction;
import com.dscsag.plm.spi.interfaces.gui.PluginRequest;
import com.dscsag.plm.spi.interfaces.gui.PluginResponse;
import com.dscsag.plm.spi.interfaces.gui.PluginResponseFactory;
import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.process.DefaultPluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;

public class PluginFunctionTestCombinedSearch implements PluginFunction
{
  @Override
  public PluginResponse actionPerformed(PluginRequest pluginRequest)
  {
    PluginResponse pluginResponse;
    String searchTerm = JOptionPane.showInputDialog(null,"search for","*815");
    DefaultPluginProcessContainer container = new DefaultPluginProcessContainer();
    container.setParameter(PluginProcessSearch.IN_MAX_HITS,50);
    container.setParameter(PluginProcessSearch.IN_SEARCH_TERM,searchTerm);
    List<PluginProcessSearch> processes = new ArrayList<>();
    processes.add(new PluginProcessSearchChangeNumberByDescription());
    processes.add(new PluginProcessSearchChangeNumberByNumber());
    processes.add(new PluginProcessSearchMaterialByDescription());
    processes.add(new PluginProcessSearchMaterialByNumber());
    container.setParameter(PluginProcessSearchCombiner.IN_SEARCH_PROCESSES, processes);
    PluginProcessSearchCombiner processSearchCombined = new PluginProcessSearchCombiner();
    try
    {
      PluginProcessContainer result = processSearchCombined.execute(container);
      List<PlmObjectKey> keys = result.getParameter(PluginProcessSearch.OUT_FOUND_KEYS);
      keys.stream().map(String::valueOf).forEach(ECTRServiceHolder.getEctrService().getPlmLogger()::trace);
      pluginResponse = PluginResponseFactory.infoResponse("found "+keys.size() + " objects.");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      pluginResponse = PluginResponseFactory.errorResponse(e.getLocalizedMessage());
    }

    return pluginResponse;
  }
}