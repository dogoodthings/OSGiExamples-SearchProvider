package org.dogoodthings.ectr.osgi.search.changeNumber;

import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;
import com.dscsag.plm.spi.rfc.builder.RfcTableBuilder;

/**
 TABLE: IT_AENR_SEL (/DSCSAG/ECM_NUMBER_SEL) , 1 row(s)
 | SIGN | OPTION | CHANGE_NUMBER_LOW | CHANGE_NUMBER_HIGH |
 | I    | EQ     | SEBASTIAN         |                    |
 */
public class PluginProcessSearchChangeNumberByNumber extends PluginProcessSearchChangeNumber
{
  @Override
  protected RfcCallBuilder prepareRfcCallBuilder(String searchTerm, int maxHits)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchTerm,maxHits);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","CHANGE_NUMBER_LOW","CHANGE_NUMBER_HIGH");
    rfcTableBuilder.addRow("I","CP","*"+searchTerm,"");
    rfcCallBuilder.addTable("IT_AENR_SEL",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }
}
