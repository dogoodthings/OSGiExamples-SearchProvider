package org.dogoodthings.ectr.osgi;

import com.dscsag.plm.spi.interfaces.ECTRService;

public class ECTRServiceHolder
{
  private static ECTRService ectrService;

  public static  ECTRService getEctrService()
  {
    return ectrService;
  }
  public static void setEctrService(ECTRService service)
  {
    ectrService = service;
  }
}