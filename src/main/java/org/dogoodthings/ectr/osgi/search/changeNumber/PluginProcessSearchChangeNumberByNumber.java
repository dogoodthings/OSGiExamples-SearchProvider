package org.dogoodthings.ectr.osgi.search.changeNumber;

import com.dscsag.plm.spi.interfaces.search.SearchMode;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchTerm;
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
  protected RfcCallBuilder prepareRfcCallBuilder(SearchQuery searchQuery)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchQuery);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","CHANGE_NUMBER_LOW","CHANGE_NUMBER_HIGH");

    for(SearchTerm term: searchQuery.getTerms())
    {
      if(term.getMode()== SearchMode.EQUALS)
        rfcTableBuilder.addRow("I", "EQ", term.getText(), "");
      else if(term.getMode()== SearchMode.PATTERN)
        rfcTableBuilder.addRow("I", "CP", "*" + term.getText()+ "*", "");
    }

    rfcCallBuilder.addTable("IT_AENR_SEL",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }

}