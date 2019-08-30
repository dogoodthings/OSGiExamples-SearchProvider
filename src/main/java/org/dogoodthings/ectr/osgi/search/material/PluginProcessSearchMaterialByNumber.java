package org.dogoodthings.ectr.osgi.search.material;

import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;
import com.dscsag.plm.spi.rfc.builder.RfcTableBuilder;

/*
 /DSCSAG/MAT_GETLIST2

 TABLE: IT_MATNRSELECTION (/DSCSAG/BAPIMATRAM) , 1 row(s)
 | SIGN | OPTION | MATNR_LOW | MATNR_HIGH |
 | I    | CP     | *816      |            |
 */
public class PluginProcessSearchMaterialByNumber extends PluginProcessSearchMaterial
{
  @Override
  protected RfcCallBuilder prepareRfcCallBuilder(String searchTerm, int maxHits)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchTerm,maxHits);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","MATNR_LOW","MATNR_HIGH");
    rfcTableBuilder.addRow("I","CP","*"+searchTerm,"");
    rfcCallBuilder.addTable("IT_MATNRSELECTION",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }
}
