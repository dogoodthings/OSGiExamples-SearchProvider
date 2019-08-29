package org.dogoodthings.ectr.osgi.search.material;

import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;
import com.dscsag.plm.spi.rfc.builder.RfcTableBuilder;

/*
 /DSCSAG/MAT_GETLIST2

 TABLE: IT_MATERIALSHORTDESCSEL (BAPIMATRAS) , 1 row(s)
 | SIGN | OPTION | DESCR_LOW | DESCR_HIGH |
 | I    | CP     | *baraxlo* |            |

 */
public class PluginProcessSearchMaterialByDescription extends PluginProcessSearchMaterial
{
  @Override
  protected RfcCallBuilder prepareRfcCallBuilder(String searchTerm, int maxHits)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchTerm,maxHits);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","DESCR_LOW","DESCR_HIGH");
    rfcTableBuilder.addRow("I","CP","*"+searchTerm+"*","");
    rfcCallBuilder.addTable("IT_MATERIALSHORTDESCSEL",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }
}