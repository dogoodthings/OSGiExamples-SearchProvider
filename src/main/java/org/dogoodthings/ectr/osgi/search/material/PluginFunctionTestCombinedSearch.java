
package org.dogoodthings.ectr.osgi.search.material;

import java.util.List;

import javax.swing.JOptionPane;

import org.dogoodthings.ectr.osgi.search.PluginProcessSearch;

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
    PluginProcessSearchMaterialCombined processSearchMaterialCombined = new PluginProcessSearchMaterialCombined();
    try
    {
      PluginProcessContainer result = processSearchMaterialCombined.execute(container);
      List<PlmObjectKey> keys = result.getParameter(PluginProcessSearch.OUT_FOUND_KEYS);
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