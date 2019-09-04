package org.dogoodthings.ectr.osgi.search.changeNumber;

import com.dscsag.plm.spi.interfaces.search.SearchMode;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchTerm;
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
  protected RfcCallBuilder prepareRfcCallBuilder(SearchQuery searchQuery)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchQuery);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","LOW","HIGH");

    for(SearchTerm term: searchQuery.getTerms())
    {
      if(term.getMode()== SearchMode.EQUALS)
        rfcTableBuilder.addRow("I", "EQ", term.getText(), "");
      else if(term.getMode()== SearchMode.PATTERN)
        rfcTableBuilder.addRow("I", "CP", "*" + term.getText()+ "*", "");
    }

    rfcCallBuilder.addTable("IT_AETXT_SEL",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }

}