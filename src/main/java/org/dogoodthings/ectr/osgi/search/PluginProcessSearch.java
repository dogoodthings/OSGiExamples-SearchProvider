package org.dogoodthings.ectr.osgi.search;

import java.util.List;

import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.process.ContainerKey;
import com.dscsag.plm.spi.interfaces.process.PluginProcess;

public abstract class PluginProcessSearch implements PluginProcess
{
  public static ContainerKey<String> IN_SEARCH_TERM = new ContainerKey<>("SEARCH_TERM");
  public static ContainerKey<Integer> IN_MAX_HITS = new ContainerKey<>("MAX_HITS");
  public static ContainerKey<List<PlmObjectKey>> OUT_FOUND_KEYS = new ContainerKey<>("FOUND_KEYS");

}