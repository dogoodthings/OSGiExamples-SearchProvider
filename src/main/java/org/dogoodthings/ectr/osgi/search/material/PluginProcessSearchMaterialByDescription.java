package org.dogoodthings.ectr.osgi.search.material;

import com.dscsag.plm.spi.interfaces.search.SearchMode;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchTerm;
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
  protected RfcCallBuilder prepareRfcCallBuilder(SearchQuery searchQuery)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchQuery);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","DESCR_LOW","DESCR_HIGH");
    for(SearchTerm term: searchQuery.getTerms())
    {
      if(term.getMode()== SearchMode.EQUALS)
        rfcTableBuilder.addRow("I", "EQ", term.getText(), "");
      else if(term.getMode()== SearchMode.PATTERN)
        rfcTableBuilder.addRow("I", "CP", "*" + term.getText()+ "*", "");
    }
    rfcCallBuilder.addTable("IT_MATERIALSHORTDESCSEL",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }
}