package org.dogoodthings.ectr.osgi.search.material;

import com.dscsag.plm.spi.interfaces.search.SearchMode;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchTerm;
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
  protected RfcCallBuilder prepareRfcCallBuilder(SearchQuery searchQuery)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchQuery);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","MATNR_LOW","MATNR_HIGH");
    for(SearchTerm term: searchQuery.getTerms())
    {
      if(term.getMode()== SearchMode.EQUALS)
        rfcTableBuilder.addRow("I", "EQ", term.getText(), "");
      else if(term.getMode()== SearchMode.PATTERN)
        rfcTableBuilder.addRow("I", "CP", "*" + term.getText()+ "*", "");
    }
    rfcCallBuilder.addTable("IT_MATNRSELECTION",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }
}
