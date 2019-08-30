package org.dogoodthings.ectr.osgi.search.changeNumber;

import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;
import com.dscsag.plm.spi.rfc.builder.RfcTableBuilder;

/**

 TABLE: IT_AETXT_SEL (/DSCSAG/RANGES_AETXT) , 1 row(s)
 | SIGN | OPTION | LOW        | HIGH |
 | I    | CP     | descriptio |      |

 */
public class PluginProcessSearchChangeNumberByDescription extends PluginProcessSearchChangeNumber
{
  @Override
  protected RfcCallBuilder prepareRfcCallBuilder(String searchTerm, int maxHits)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchTerm,maxHits);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","LOW","HIGH");
    rfcTableBuilder.addRow("I","CP","*"+searchTerm+"*","");
    rfcCallBuilder.addTable("IT_AETXT_SEL",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }
}