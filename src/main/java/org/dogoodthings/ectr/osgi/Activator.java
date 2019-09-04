package org.dogoodthings.ectr.osgi;

import org.dogoodthings.ectr.osgi.search.provider.changeNumber.ChangeNumberSearchProvider;
import org.dogoodthings.ectr.osgi.search.provider.material.MaterialSearchProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.gui.PluginFunctionService;
import com.dscsag.plm.spi.interfaces.search.SearchProvider;

/**
 * Activator to register provided services
 */
public class Activator implements BundleActivator
{
  public void start(BundleContext context) throws Exception
  {
    ECTRServiceHolder.setEctrService(getService(context, ECTRService.class));
    context.registerService(PluginFunctionService.class, new PluginFunctionManager(), null);
    context.registerService(SearchProvider.class, new MaterialSearchProvider(), null);
    context.registerService(SearchProvider.class, new ChangeNumberSearchProvider(), null);
  }

  @Override
  public void stop(BundleContext bundleContext) throws Exception
  {
    //empty
  }

  private <T> T getService(BundleContext context, Class<T> clazz) throws Exception
  {
    ServiceReference<T> serviceRef = context.getServiceReference(clazz);
    if (serviceRef != null)
      return context.getService(serviceRef);
    throw new Exception("Unable to find implementation for service " + clazz.getName());
  }
}